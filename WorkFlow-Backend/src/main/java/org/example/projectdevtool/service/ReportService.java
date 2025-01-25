package org.example.projectdevtool.service;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.AllArgsConstructor;
import org.example.projectdevtool.dto.ReportResponse;
import org.example.projectdevtool.entity.Profile;
import org.example.projectdevtool.entity.Project;
import org.example.projectdevtool.entity.Report;
import org.example.projectdevtool.entity.Task;
import org.example.projectdevtool.repo.ProfileRepo;
import org.example.projectdevtool.repo.ProjectRepo;
import org.example.projectdevtool.repo.ReportRepo;
import org.example.projectdevtool.repo.TaskRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ReportService {

    private final ReportRepo reportRepo;
    private final ProjectRepo projectRepo;
    private final TaskRepo taskRepo;
    private final ProfileRepo profileRepo;

    public byte[] getReportById(Long projectId) {
        return generateReportPdf(findReport(projectId));
    }

    private Report makeReportReady(Project project, List<Task> tasks, List<Profile> employees) {
        Report report = new Report();

        List<Long> taskIds = new ArrayList<>();
        for(Task task : tasks){
            taskIds.add(task.getId());
        }

        List<Long> employeesId = new ArrayList<>();
        for(Profile emp : employees){
            employeesId.add(emp.getId());
        }

        List<Long> completedTaskIds = new ArrayList<>();

        for (Task task: tasks){
            if (task.getStatus().toString().equals("COMPLETED")){
                completedTaskIds.add(task.getId());
            }
        }

        tasks.sort(
                Comparator.comparing(Task::getStartDate)  // Sort by startDate first
        );

        LocalDate startAt = tasks.getFirst().getStartDate();
        LocalDate endAt = tasks.getLast().getEndDate();

        int taskRateSum = 0;

        for(Task task : tasks){
            taskRateSum += task.getScore();
        }

        double qualityScore = (double) taskRateSum/taskIds.size();

        double completedRate = (double) (completedTaskIds.size()/taskIds.size()) * 100;
        report.setProject(project);
        report.setTotalTasks(taskIds);
        report.setEmployees(employeesId);
        report.setSummary("");
        report.setStartAt(startAt);
        report.setEndAt(endAt);
        report.setSpentTime((endAt.getDayOfMonth() - startAt.getDayOfMonth()) +" days");
        report.setTasksCompleted(completedTaskIds);
        report.setBudgetUsed(project.getBudget());
        report.setTotalBudget(project.getBudget());
        report.setCompletedRate(completedRate);
        report.setTeamSize(employees.size() + 1);
        report.setTeamPerformance("good");
        report.setQualityScore(qualityScore);

        return reportRepo.save(report);
    }

    public byte[] generateReportPdf(ReportResponse report) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Initialize PDF writer and document
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add report content to the PDF
            document.add(new Paragraph("Project Development Report"));
            document.add(new Paragraph("---------------------------------------------------"));
            document.add(new Paragraph("Project ID: " + report.getId()));
            document.add(new Paragraph("Completed Rate: " + report.getCompletedRate() + "%"));
            document.add(new Paragraph("Team Performance: " + report.getTeamPerformance()));
            document.add(new Paragraph("Spent Time: " + report.getSpentTime()));
            document.add(new Paragraph("Tasks Completed: " + report.getTasksCompleted() + "/" + report.getTotalTasks()));
            document.add(new Paragraph("Team Size: " + report.getTeamSize()));
            document.add(new Paragraph("Budget Used: $" + report.getBudgetUsed() + " / $" + report.getTotalBudget()));
            document.add(new Paragraph("Quality Score: " + report.getQualityScore()));
            document.add(new Paragraph("Summary: " + report.getSummary()));

            // Close the document
            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    public ReportResponse findReport(Long id){
        Project project = projectRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("not found"));

        List<Task> tasks = taskRepo.findByProject(project);

        Report report = makeReportReady(project, tasks, project.getEmployees());

        return new ReportResponse(
                report.getProject().getId(),
                report.getProject().getName(),
                report.getProject().getDescription(),
                report.getCompletedRate(),
                report.getTeamPerformance(), //
                report.getSpentTime(),
                report.getStartAt(),
                report.getEndAt(),
                report.getTasksCompleted(),
                report.getTotalTasks(),
                report.getTeamSize(),
                report.getEmployees(),
                report.getBudgetUsed(),
                report.getTotalBudget(),
                report.getQualityScore(), //
                report.getSummary()
        );
    }
}
