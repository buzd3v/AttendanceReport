package demo.demo.ImportReport.Employee;

public class Employee {

    public Employee(String id, Kind kind) {
        this.id = id;
        this.kind = kind;
    }


    private String id;
    private Kind kind;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }
}
