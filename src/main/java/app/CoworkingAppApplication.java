package app;

import app.ui.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CoworkingAppApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(CoworkingAppApplication.class, args);

        ConsoleUI console = ctx.getBean(ConsoleUI.class);
        AdminConsoleWorkflow adminFlow = ctx.getBean(AdminConsoleWorkflow.class);
        CustomerConsoleWorkflow customerFlow = ctx.getBean(CustomerConsoleWorkflow.class);

        boolean running = true;
        while (running) {
            console.println("\nMain Menu:\n1. Admin\n2. Customer\n3. Exit");
            int choice = console.promptInt("Choose user type: ");
            switch (choice) {
                case 1 -> adminFlow.run();
                case 2 -> customerFlow.run();
                case 3 -> running = false;
                default -> console.printError("Invalid option.");
            }
        }

        console.println("Goodbye!");
    }
}
