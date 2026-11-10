import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String password1 = "admin123";
        String password2 = "vendedor123";
        
        System.out.println("admin123: " + encoder.encode(password1));
        System.out.println("vendedor123: " + encoder.encode(password2));
        
        // Verificar que funciona
        String hash = encoder.encode(password1);
        System.out.println("\nVerificando admin123: " + encoder.matches(password1, hash));
    }
}
