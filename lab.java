import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Optional;

@SpringBootApplication
public class HospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }
}

@Entity
@Table(name = "doctor")
class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String name;
    private String department;
    private String status;

    // Getters and Setters

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

@Entity
@Table(name = "patient")
class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    private String name;
    private Date dateOfBirth;
    private Long admittedBy; // Assuming this is the doctor's employeeId

    // Getters and Setters

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getAdmittedBy() {
        return admittedBy;
    }

    public void setAdmittedBy(Long admittedBy) {
        this.admittedBy = admittedBy;
    }
}

@Repository
interface DoctorRepository extends JpaRepository<Doctor, Long> {
}

@Repository
interface PatientRepository extends JpaRepository<Patient, Long> {
}

@RestController
@RequestMapping("/api/doctors")
class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public Doctor addNewDoctor(@RequestBody Doctor doctor) {
        return doctorService.addDoctor(doctor);
    }

    @PutMapping("/{employeeId}/status")
    public Doctor changeDoctorStatus(@PathVariable Long employeeId, @RequestBody String status) {
        return doctorService.changeDoctorStatus(employeeId, status);
    }

    @PutMapping("/{employeeId}/department")
    public Doctor updateDoctorDepartment(@PathVariable Long employeeId, @RequestBody String department) {
        return doctorService.updateDoctorDepartment(employeeId, department);
    }
}

@RestController
@RequestMapping("/api/patients")
class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PatchMapping("/{patientId}")
    public Patient updatePatientInfo(@PathVariable Long patientId, @RequestBody Patient patientDetails) {
        return patientService.updatePatient(patientId, patientDetails);
    }
}

@Service
class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor changeDoctorStatus(Long employeeId, String status) {
        Doctor doctor = doctorRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + employeeId));
        doctor.setStatus(status);
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctorDepartment(Long employeeId, String department) {
        Doctor doctor = doctorRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + employeeId));
        doctor.setDepartment(department);
        return doctorRepository.save(doctor);
    }
}

@Service
class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient updatePatient(Long patientId, Patient patientDetails) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));

        if (patientDetails.getName() != null) {
            patient.setName(patientDetails.getName());
        }
        if (patientDetails.getDateOfBirth() != null) {
            patient.setDateOfBirth(patientDetails.getDateOfBirth());
        }
        if (patientDetails.getAdmittedBy() != null) {
            patient.setAdmittedBy(patientDetails.getAdmittedBy());
        }

        return patientRepository.save(patient);
    }
}
