import java.util.HashMap;
import java.util.Map;

enum ModeIdentifier {
    DUMP,
    PASSTHROUGH,
    VALIDATE
}

enum DatabaseIdentifier {
    POSTGRES,
    REDIS,
    ELASTIC
}

class DataPoint {
    private final String payload;

    public DataPoint(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }
}

interface DatabaseConnection {
    void connect();
    void insert(DataPoint data);
    boolean validate(DataPoint data);
}

class PostgresDatabase implements DatabaseConnection {
    public void connect() { System.out.println("[Postgres] Connected."); }
    public void insert(DataPoint data) { System.out.println("[Postgres] Inserted: " + data.getPayload()); }
    public boolean validate(DataPoint data) { 
        System.out.println("[Postgres] Validating data...");
        return data.getPayload() != null && !data.getPayload().isEmpty(); 
    }
}

class RedisDatabase implements DatabaseConnection {
    public void connect() { System.out.println("[Redis] Connected."); }
    public void insert(DataPoint data) { System.out.println("[Redis] Cached: " + data.getPayload()); }
    public boolean validate(DataPoint data) { 
        System.out.println("[Redis] Validating cache key...");
        return data.getPayload() != null; 
    }
}

class ElasticDatabase implements DatabaseConnection {
    public void connect() { System.out.println("[Elastic] Connected."); }
    public void insert(DataPoint data) { System.out.println("[Elastic] Indexed document: " + data.getPayload()); }
    public boolean validate(DataPoint data) { 
        System.out.println("[Elastic] Validating index structure...");
        return data.getPayload() != null; 
    }
}

public class DataProcessorSystem {

    private ModeIdentifier currentMode = ModeIdentifier.DUMP;
    private DatabaseConnection currentDb;
    private final Map<DatabaseIdentifier, DatabaseConnection> dbRegistry = new HashMap<>();

    public DataProcessorSystem() {
        dbRegistry.put(DatabaseIdentifier.POSTGRES, new PostgresDatabase());
        dbRegistry.put(DatabaseIdentifier.REDIS, new RedisDatabase());
        dbRegistry.put(DatabaseIdentifier.ELASTIC, new ElasticDatabase());
        
        // Default DB setup
        configure(ModeIdentifier.DUMP, DatabaseIdentifier.POSTGRES);
    }

    public void configure(ModeIdentifier mode, DatabaseIdentifier dbId) {
        this.currentMode = mode;
        this.currentDb = dbRegistry.get(dbId);
        if (this.currentDb != null) {
            this.currentDb.connect();
        }
        System.out.println("Configured Processor to Mode: " + mode + " with DB: " + dbId);
    }

    public void process(DataPoint data) {
        if (data == null) return;

        switch (currentMode) {
            case DUMP:
                System.out.println("[DUMP MODE] Dropping data point: " + data.getPayload());
                break;

            case PASSTHROUGH:
                System.out.println("[PASSTHROUGH MODE] Bypassing validation.");
                if (currentDb != null) {
                    currentDb.insert(data);
                }
                break;

            case VALIDATE:
                System.out.println("[VALIDATE MODE] Running validations.");
                if (currentDb != null) {
                    if (currentDb.validate(data)) {
                        currentDb.insert(data);
                    } else {
                        System.out.println("[VALIDATE MODE] Data validation failed!");
                    }
                }
                break;
        }
    }

    public static void main(String[] args) {
        DataProcessorSystem processor = new DataProcessorSystem();
        DataPoint sampleData = new DataPoint("Walmart Order #99182");

        System.out.println("\n--- Test 1: Dump Mode ---");
        processor.configure(ModeIdentifier.DUMP, DatabaseIdentifier.POSTGRES);
        processor.process(sampleData);

        System.out.println("\n--- Test 2: Passthrough Mode with Redis ---");
        processor.configure(ModeIdentifier.PASSTHROUGH, DatabaseIdentifier.REDIS);
        processor.process(sampleData);

        System.out.println("\n--- Test 3: Validate Mode with Elastic ---");
        processor.configure(ModeIdentifier.VALIDATE, DatabaseIdentifier.ELASTIC);
        processor.process(sampleData);
    }
}