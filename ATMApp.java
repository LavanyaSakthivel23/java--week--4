public class ATMApp {
    public static void main(String[] args) {
        AccountRepository repository = new AccountRepository();
        ATMView view = new ATMView();
        ATMController controller = new ATMController(repository, view);
        controller.start();
    }
}