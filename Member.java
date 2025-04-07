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

class Member {
    private String name;
    private int age;
    private String contact;
    private String subscriptionType;
    private boolean personalTrainer;
    private String memberId;
    private double price;
    private LocalDate expirationDate;
    private String slot;
    private String trainingType;
    private String trainer;
    private String paymentType;

    public Member() {
        this.memberId = UUID.randomUUID().toString().substring(0, 8); 
    }

    public String getMemberId() {
        return memberId;     }

    public void inputDetails(List<Trainer> trainers) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Name: ");
        name = scanner.nextLine();

        try {
            System.out.print("Enter Age: ");
            age = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Enter Contact: ");
            contact = scanner.nextLine();

            System.out.print("Select Subscription Type (Monthly/Quarterly/Yearly): ");
            subscriptionType = scanner.nextLine();

            System.out.print("Do you want a personal trainer? (yes/no): ");
            String trainerChoice = scanner.nextLine();
            personalTrainer = trainerChoice.equalsIgnoreCase("yes");

            if (personalTrainer) {
                chooseTrainingType();
                chooseTrainer(trainers);
            }

            calculatePrice();
            setExpirationDate();
            chooseSlot();
            
            if (confirmPayment()) {  
                choosePaymentType();
                saveToFile();
System.out.println("Payment Successful!!");
System.out.println("Member ID: " + memberId);
            } else {
                System.out.println("Payment not confirmed. Registration is incomplete.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please enter the correct data format.");
            scanner.nextLine(); 
        } catch (IOException e) {
            System.out.println("An error occurred while saving to file.");
            e.printStackTrace();
        }
    }

    private void choosePaymentType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select Payment Type:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println("3. Mobile Transaction");

        int paymentChoice;
        try {
            paymentChoice = scanner.nextInt();
            switch (paymentChoice) {
                case 1:
                    paymentType = "Cash";
                    break; 
                case 2:
                    paymentType = "Card";
                    break; 
                case 3:
                    paymentType = "Mobile Transaction";
                    break; 
                default:
                    System.out.println("Invalid choice! Defaulting to Cash.");
                    paymentType = "Cash";
            }
            System.out.println("Payment Method Selected: " + paymentType);
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice! Defaulting to Cash.");
            paymentType = "Cash";
        }
    }

    private void chooseTrainingType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose your training type:");
        System.out.println("1. Weight Loss");
        System.out.println("2. Weight Gain");
        System.out.println("3. General Fitness");
        System.out.print("Select your training type: ");
        int typeChoice = scanner.nextInt();
        switch (typeChoice) {
            case 1:
                trainingType = "Weight Loss";
                break;
            case 2:
                trainingType = "Weight Gain";
                break;
            case 3:
                trainingType = "General Fitness";
                break;
            default:
                System.out.println("Invalid choice! Defaulting to General Fitness.");
                trainingType = "General Fitness";
                break;
        }
    }

    private void chooseTrainer(List<Trainer> trainers) {
    System.out.println("Available Trainers for " + trainingType + ":");
    for (int i = 0; i < trainers.size(); i++) {
        Trainer trainer = trainers.get(i);
        System.out.print((i + 1) + ". " + trainer.getName() + " (Booked Members: " + trainer.getBookedMembers() + ")");
        if (trainer.getBookedMembers() >= 3) {
            System.out.println(" - Fully Booked");
        } else {
            System.out.println();
        }
    }

    Scanner scanner = new Scanner(System.in);
    System.out.print("Select a trainer by number: ");
    
    try {
        int selectedIndex = scanner.nextInt() - 1; // Get the selected index (zero-based)

        if (selectedIndex >= 0 && selectedIndex < trainers.size()) {
            Trainer chosenTrainer = trainers.get(selectedIndex); // Renamed variable
            if (chosenTrainer.getBookedMembers() < 3) {
                this.trainer = chosenTrainer.getName();
                chosenTrainer.incrementBookedMembers();
                System.out.println("Trainer " + chosenTrainer.getName() + " booked successfully!");
            } else {
                System.out.println("Sorry, Trainer " + chosenTrainer.getName() + " has already been booked. Please choose another trainer.");
                chooseTrainer(trainers); // Recurse to choose again
            }
        } else {
            System.out.println("Invalid choice! Please choose a valid trainer number.");
            chooseTrainer(trainers); // Recurse to choose again
        }
    } catch (InputMismatchException e) {
        System.out.println("Invalid input! Please enter a number.");
        scanner.nextLine(); // Clear the invalid input
        chooseTrainer(trainers); // Recurse to choose again
    }
}

    private void calculatePrice() {
        switch (subscriptionType.toLowerCase()) {
            case "monthly":
                price = 3000;
                break;
            case "quarterly":
                price = 7000;
                break;
            case "yearly":
                price = 10000;
                break;
            default:
                System.out.println("Invalid subscription type!");
                price = 0;
                break;
        }

        if (personalTrainer) {
            price += 4000;
        }

        System.out.println("Total Price: Rs. " + price);
    }

    private void setExpirationDate() {
        LocalDate currentDate = LocalDate.now();
        switch (subscriptionType.toLowerCase()) {
            case "monthly":
                expirationDate = currentDate.plusMonths(1);
                break;
            case "quarterly":
                expirationDate = currentDate.plusMonths(3);
                break;
            case "yearly":
                expirationDate = currentDate.plusYears(1);
                break;
            default:
                expirationDate = currentDate;
                break;
        }

        System.out.println("Subscription valid until: " + expirationDate);
    }

    private void chooseSlot() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a slot:");
        System.out.println("1. 5:00 to 6:30 AM");
        System.out.println("2. 6:10 to 8:00 AM");
        System.out.println("3. 8:20 to 9:20 AM");
        System.out.println("4. 4:30 to 5:30 PM");
        System.out.println("5. 5:00 to 6:30 PM");
        System.out.println("6. 6:10 to 8:00 PM");
        System.out.println("7. 8:10 to 9:30 PM");

        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    slot = "5:00 to 6:30 AM";
                    break;
                case 2:
                    slot = "6:10 to 8:00 AM";
                    break;
                case 3:
                    slot = "8:20 to 9:20 AM";
                    break;
                case 4:
                    slot = "4:30 to 5:30 PM";
                    break;
                case 5:
                    slot = "5:00 to 6:30 PM";
                    break;
                case 6:
                    slot = "6:10 to 8:00 PM";
                    break;
                case 7:
                    slot = "8:10 to 9:30 PM";
                    break;
                default:
                    System.out.println("Invalid choice!");
                    slot = "No slot selected";
                    break;
            }

            System.out.println("Selected Slot: " + slot);
        } catch (InputMismatchException e) {
            System.out.println("Invalid slot selection. Please enter a number.");
            scanner.nextLine(); 
            chooseSlot(); 
        }
    }

    private void saveToFile() throws IOException {
        String filename = "members.txt";         FileWriter writer = new FileWriter(filename, true);
        writer.write("Member ID: " + memberId + "\n");
        writer.write("Name: " + name + "\n");
        writer.write("Age: " + age + "\n");
        writer.write("Contact: " + contact + "\n");
        writer.write("Subscription Type: " + subscriptionType + "\n");
        writer.write("Personal Trainer: " + (personalTrainer ? "Yes" : "No") + "\n");
        if (personalTrainer) {
            writer.write("Training Type: " + trainingType + "\n");
            writer.write("Trainer: " + trainer + "\n");
        }
        writer.write("Price: " + price + "\n");
        writer.write("Expiration Date: " + expirationDate + "\n");
        writer.write("Slot: " + slot + "\n");
        writer.write("Payment Type: " + paymentType + "\n");
        writer.write("--------------------------------------------------\n");
        writer.close();
        System.out.println("Member details saved to " + filename);
    }

    private boolean confirmPayment() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Confirm payment? (yes/no): ");
        String confirmation = scanner.nextLine();
        return confirmation.equalsIgnoreCase("yes");
    }

    public void cancelBooking(List<Trainer> trainers) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the trainer to cancel booking: ");
        String trainerName = scanner.nextLine();
        for (Trainer trainer : trainers) {
            if (trainer.getName().equalsIgnoreCase(trainerName)) {
                trainer.incrementBookedMembers();                System.out.println("Booking with " + trainerName + " has been cancelled.");
                return;
            }
        }
        System.out.println("Trainer not found.");
    }

    public void cancelSlot() {
                System.out.println("Slot " + slot + " has been cancelled.");
        slot = null; 
    }

    public static void main(String[] args) {
        List<Trainer> trainers = new ArrayList<>(); 
        Member member = new Member();
        member.inputDetails(trainers);
    }
}