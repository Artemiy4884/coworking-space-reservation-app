package app.ui;

import app.services.AuthService;
import app.services.AdminService;
import app.services.CommonService;
import app.utils.CollectionsDisplayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class AdminConsoleWorkflow {

    private final ConsoleUI console;
    private final AdminService adminService;
    private final AuthService authService;
    private final CommonService commonService;
    private final CollectionsDisplayer displayer;

    @Autowired
    public AdminConsoleWorkflow(ConsoleUI console, AdminService adminService, AuthService authService, CommonService commonService) {
        this.console = console;
        this.adminService = adminService;
        this.authService = authService;
        this.commonService = commonService;
        this.displayer = new CollectionsDisplayer(console);
    }

    public void run() {
        String username = console.prompt("Enter admin username: ");
        String password = console.prompt("Enter password: ");

        if (!authService.validateUser(username, password, "admin")) {
            console.printError("Invalid admin credentials.");
            return;
        }

        AtomicBoolean inAdminMenu = new AtomicBoolean(true);
        while (inAdminMenu.get()) {
            console.println("\nAdmin Menu:\n1. Add space\n2. Remove space" +
                    "\n3. Display all reservations\n4. Exit");
            int choice = console.promptInt("Your choice: ");

            switch (choice) {
                case 1 -> {
                    String type = console.prompt("Enter space type: ");
                    String details = console.prompt("Enter space details: ");
                    BigDecimal price = new BigDecimal(console.prompt("Enter price: "));
                    adminService.addSpace(type, details, price);
                    console.println("Space added.");
                }
                case 2 -> {
                    displayer.displayList(commonService.getAllCoworkingSpaces());

                    int id = console.promptInt("Enter space ID to remove: ");
                    boolean removed = adminService.removeSpace(id);
                    console.println(removed ? "Space removed." : "ID not found.");
                }
                case 3 -> displayer.displayList(adminService.getReservations());
                case 4 -> inAdminMenu.set(false);
                default -> console.printError("Invalid option.");
            }
        }
    }
}
