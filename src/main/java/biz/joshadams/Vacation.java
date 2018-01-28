package biz.joshadams;
import java.time.LocalDate;

public class Vacation {
    private LocalDate start;
    private LocalDate end;

    public Vacation(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }
}
