package com.example.andersontimetracking.Tests;

import com.example.andersontimetracking.models.Task;
import com.example.andersontimetracking.models.User;
import com.example.andersontimetracking.services.PdfGenerator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class PdfGeneratorTest {
    private PdfGenerator pdfGenerator;
    private List<User> users;

    @BeforeEach
    void setUp() {
        pdfGenerator = new PdfGenerator();
        users = new ArrayList<>();
        User user = new User();
        user.setName("Test");
        user.setSurname("User");
        Task task = new Task();
        task.setDescription("Test task");
        user.getTasks().add(task);
        users.add(user);
    }

    @Test
    void generateReport() {
        PDDocument document = pdfGenerator.generateReport(users);
        assertNotNull(document);
        assertEquals(1, document.getNumberOfPages());
        System.out.println("Sucsess");
    }
}
