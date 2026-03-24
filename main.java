// ================= ENUMS =================
public enum VehicleType {
    ECONOMY, LUXURY, SUV, BIKE, AUTO
}

public enum VehicleStatus {
    AVAILABLE, RESERVED, RENTED, MAINTENANCE, OUT_OF_SERVICE
}

public enum ReservationStatus {
    PENDING, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELED
}


// ================= VEHICLE =================
public abstract class Vehicle {
    private String registrationNumber;
    private String model;
    private VehicleType type;
    private VehicleStatus status;
    private double baseRentalPrice;

    public Vehicle(String registrationNumber, String model, VehicleType type, double baseRentalPrice) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.type = type;
        this.status = VehicleStatus.AVAILABLE;
        this.baseRentalPrice = baseRentalPrice;
    }

    public abstract double calculateRentalFee(int days);

    public String getRegistrationNumber() { return registrationNumber; }
    public String getModel() { return model; }
    public VehicleType getType() { return type; }
    public VehicleStatus getStatus() { return status; }
    public void setStatus(VehicleStatus status) { this.status = status; }
    public double getBaseRentalPrice() { return baseRentalPrice; }
}


// ================= CONCRETE VEHICLES =================
public class EconomyVehicle extends Vehicle {
    private static final double RATE_MULTIPLIER = 1.0;

    public EconomyVehicle(String reg, String model, VehicleType type, double price) {
        super(reg, model, type, price);
    }

    public double calculateRentalFee(int days) {
        return getBaseRentalPrice() * days * RATE_MULTIPLIER;
    }
}

public class LuxuryVehicle extends Vehicle {
    private static final double RATE_MULTIPLIER = 2.5;
    private static final double PREMIUM_FEE = 50.0;

    public LuxuryVehicle(String reg, String model, VehicleType type, double price) {
        super(reg, model, type, price);
    }

    public double calculateRentalFee(int days) {
        return (getBaseRentalPrice() * days * RATE_MULTIPLIER) + PREMIUM_FEE;
    }
}

public class SUVVehicle extends Vehicle {
    private static final double RATE_MULTIPLIER = 1.5;

    public SUVVehicle(String reg, String model, VehicleType type, double price) {
        super(reg, model, type, price);
    }

    public double calculateRentalFee(int days) {
        return getBaseRentalPrice() * days * RATE_MULTIPLIER;
    }
}

public class BikeVehicle extends Vehicle {
    private static final double RATE_MULTIPLIER = 0.5;

    public BikeVehicle(String reg, String model, VehicleType type, double price) {
        super(reg, model, type, price);
    }

    public double calculateRentalFee(int days) {
        return getBaseRentalPrice() * days * RATE_MULTIPLIER;
    }
}

public class AutoVehicle extends Vehicle {
    private static final double RATE_MULTIPLIER = 1.2;

    public AutoVehicle(String reg, String model, VehicleType type, double price) {
        super(reg, model, type, price);
    }

    public double calculateRentalFee(int days) {
        return getBaseRentalPrice() * days * RATE_MULTIPLIER;
    }
}


// ================= VEHICLE FACTORY =================
public class VehicleFactory {
    public static Vehicle createVehicle(VehicleType type, String reg, String model, double price) {
        switch (type) {
            case ECONOMY: return new EconomyVehicle(reg, model, type, price);
            case LUXURY: return new LuxuryVehicle(reg, model, type, price);
            case SUV: return new SUVVehicle(reg, model, type, price);
            case BIKE: return new BikeVehicle(reg, model, type, price);
            case AUTO: return new AutoVehicle(reg, model, type, price);
            default: throw new IllegalArgumentException("Invalid type");
        }
    }
}


// ================= LOCATION =================
public class Location {
    private String address;
    private String city;
    private String state;
    private String zipCode;

    public Location(String address, String city, String state, String zipCode) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
}


// ================= RENTAL STORE =================
import java.util.*;

public class RentalStore {
    private int id;
    private String name;
    private Location location;
    private Map<String, Vehicle> vehicles = new HashMap<>();

    public RentalStore(int id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public void addVehicle(Vehicle v) {
        vehicles.put(v.getRegistrationNumber(), v);
    }

    public void removeVehicle(String reg) {
        vehicles.remove(reg);
    }

    public Vehicle getVehicle(String reg) {
        return vehicles.get(reg);
    }

    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> list = new ArrayList<>();
        for (Vehicle v : vehicles.values()) {
            if (v.getStatus() == VehicleStatus.AVAILABLE) {
                list.add(v);
            }
        }
        return list;
    }
}


// ================= USER =================
import java.util.*;

public class User {
    private int id;
    private String name;
    private String email;
    private List<Reservation> reservations = new ArrayList<>();

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() { return id; }

    public void addReservation(Reservation r) {
        reservations.add(r);
    }
}


// ================= RESERVATION =================
import java.util.*;

public class Reservation {
    private int id;
    private User user;
    private Vehicle vehicle;
    private Date startDate;
    private Date endDate;
    private ReservationStatus status;
    private double totalAmount;

    public Reservation(int id, User user, Vehicle vehicle, Date start, Date end) {
        this.id = id;
        this.user = user;
        this.vehicle = vehicle;
        this.startDate = start;
        this.endDate = end;
        this.status = ReservationStatus.PENDING;

        long diff = end.getTime() - start.getTime();
        int days = (int)(diff / (1000 * 60 * 60 * 24)) + 1;
        this.totalAmount = vehicle.calculateRentalFee(days);
    }

    public int getId() { return id; }
    public double getTotalAmount() { return totalAmount; }

    public void confirm() {
        status = ReservationStatus.CONFIRMED;
        vehicle.setStatus(VehicleStatus.RESERVED);
    }
}


// ================= RESERVATION MANAGER =================
import java.util.*;

public class ReservationManager {
    private Map<Integer, Reservation> map = new HashMap<>();
    private int id = 1;

    public Reservation create(User user, Vehicle v, Date s, Date e) {
        Reservation r = new Reservation(id++, user, v, s, e);
        map.put(r.getId(), r);
        user.addReservation(r);
        return r;
    }

    public Reservation get(int id) {
        return map.get(id);
    }
}


// ================= PAYMENT =================
public interface PaymentStrategy {
    void pay(double amount);
}

public class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid by Credit Card: " + amount);
    }
}

public class CashPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid by Cash: " + amount);
    }
}

public class PaymentProcessor {
    public boolean process(double amount, PaymentStrategy p) {
        p.pay(amount);
        return true;
    }
}


// ================= RENTAL SYSTEM (SINGLETON) =================
import java.util.*;

public class RentalSystem {
    private static RentalSystem instance;
    private List<RentalStore> stores = new ArrayList<>();
    private Map<Integer, User> users = new HashMap<>();
    private ReservationManager manager = new ReservationManager();
    private PaymentProcessor processor = new PaymentProcessor();

    private RentalSystem() {}

    public static synchronized RentalSystem getInstance() {
        if (instance == null) instance = new RentalSystem();
        return instance;
    }

    public void addStore(RentalStore s) { stores.add(s); }

    public void registerUser(User u) { users.put(u.getId(), u); }

    public Reservation createReservation(int userId, Vehicle v, Date s, Date e) {
        return manager.create(users.get(userId), v, s, e);
    }

    public boolean pay(int reservationId, PaymentStrategy p) {
        Reservation r = manager.get(reservationId);
        if (r != null) {
            processor.process(r.getTotalAmount(), p);
            r.confirm();
            return true;
        }
        return false;
    }
}
