package hello.jdbc.connection;

public abstract class ConnectionConst { //abstract 다른데서 생성하지 못하게 함. 상수로 해놨기 때문에 아래 변수들이 다른데서 객체로 선언해서 사용하면 안된다.

    public static final String URL = "jdbc:h2:tcp://localhost/~/test";  //여기 안에서만 사용할꺼면 private / 외부 사용이면 public
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";

}
