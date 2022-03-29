package emsi.jpaap;

import emsi.jpaap.entities.Patient;
import emsi.jpaap.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.xml.bind.SchemaOutputResolver;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class JpaApApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {

        SpringApplication.run(JpaApApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i=0;i<100;i++) {
            patientRepository.save(new Patient(null, "hatim", new Date(), false, (int)(Math.random()*100)));
        }
        Page<Patient> patients=patientRepository.findAll(PageRequest.of(0,5));
        System.out.println("Totale de page :"+patients.getTotalPages());
        System.out.println("Totale d'element :"+patients.getTotalElements());
        System.out.println("Num page "+patients.getNumber());
        List<Patient> content = patients.getContent();
        content.forEach(
                p->{
                    System.out.println("====================================");
                    System.out.println(p.getId());
                    System.out.println(p.getNom());
                    System.out.println(p.getDateNaissance());
                    System.out.println(p.getScore());
                    System.out.println(p.isMalade());

                }
        );
        System.out.println("********************************");
        Patient patient = patientRepository.findById(1L).orElse(null);//1L = new Long (1)
        if(patient != null){
            System.out.println(patient.getNom());
            System.out.println(patient.isMalade());
        }
        patient.setScore(230);
        patientRepository.save(patient);
        patientRepository.deleteById(1L);


    }
}
