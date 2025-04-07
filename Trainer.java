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

 class Trainer {
    private String name;
    private int bookedMembers;

    public Trainer(String name) {
        this.name = name;
        this.bookedMembers = 0;
    }

    public String getName() {
        return name;
    }

    public int getBookedMembers() {
        return bookedMembers;
    }

    public void incrementBookedMembers() {
        bookedMembers++;
    }
}
