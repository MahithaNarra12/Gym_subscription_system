import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
public class GymSubscriptionSystem {
    public static void main(String[] args) {
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(new Trainer("Rahul"));
        trainers.add(new Trainer("AAyush"));
        trainers.add(new Trainer("Priyanka"));
        trainers.add(new Trainer("Ashish"));
        trainers.add(new Trainer("AAshna"));

                
        Scanner scanner = new Scanner(System.in);
        List<Member> members = new ArrayList<>();

        while (true) {
            System.out.println("Gym Subscription System");
            System.out.println("1. Register Member");
            System.out.println("2. Cancel Booking");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    Member newMember = new Member();
                    newMember.inputDetails(trainers);
                    members.add(newMember);
                    break;
                case 2:
                    System.out.print("Enter Member ID to cancel booking: ");
                    String memberIdToCancel = scanner.nextLine();
                    for (Member member : members) {
                        if (member.getMemberId().equals(memberIdToCancel)) {
                            member.cancelBooking(trainers);
                            member.cancelSlot();
                            break;
                        }
                    }
                    break;
                case 3:
                    System.out.println("Exiting the system.");
                    return;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }
}