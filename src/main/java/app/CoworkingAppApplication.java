package app;

import app.ui.AdminConsoleWorkflow;
import app.ui.ConsoleUI;
import app.ui.CustomerConsoleWorkflow;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CoworkingAppApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

        ConsoleUI console = appContext.getBean(ConsoleUI.class);
        AdminConsoleWorkflow adminFlow = appContext.getBean(AdminConsoleWorkflow.class);
        CustomerConsoleWorkflow customerFlow = appContext.getBean(CustomerConsoleWorkflow.class);

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
        appContext.close();
    }
}
