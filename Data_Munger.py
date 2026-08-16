import csv
import sqlite3

def safe_int(val, default=1):
    """Safely convert string to int, defaulting to 1 if parsing fails."""
    try:
        return int(val)
    except (ValueError, TypeError):
        return default

def populate_database(db_path, csv0_path, csv1_path, csv2_path):
    conn = sqlite3.connect(db_path)
    cursor = conn.cursor()

    # Create Tables if they don't exist
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS product (
            name TEXT PRIMARY KEY,
            type TEXT
        );
    """)
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS shipment (
            shipment_id TEXT PRIMARY KEY,
            origin TEXT,
            destination TEXT
        );
    """)
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS shipment_item (
            shipment_id TEXT,
            product_name TEXT,
            quantity INTEGER
        );
    """)

    # 1. Process Spreadsheet 0 (Product data)
    with open(csv0_path, mode='r', encoding='utf-8-sig') as file:
        reader = csv.reader(file)
        next(reader, None)  # Skip header row
        for row in reader:
            if len(row) >= 2:
                cursor.execute("""
                    INSERT OR IGNORE INTO product (name, type)
                    VALUES (?, ?)
                """, (row[0].strip(), row[1].strip()))

    # 2. Process Spreadsheet 2 (Shipment Origin and Destination)
    shipment_locations = {}
    with open(csv2_path, mode='r', encoding='utf-8-sig') as file:
        reader = csv.reader(file)
        next(reader, None)  # Skip header row
        for row in reader:
            if len(row) >= 3:
                shipment_locations[row[0].strip()] = {
                    'origin': row[1].strip(),
                    'destination': row[2].strip()
                }

    # 3. Process Spreadsheet 1 (Products per Shipment)
    with open(csv1_path, mode='r', encoding='utf-8-sig') as file:
        reader = csv.reader(file)
        next(reader, None)  # Skip header row
        for row in reader:
            if len(row) >= 3:
                shipment_id = row[0].strip()
                product_name = row[1].strip()
                quantity = safe_int(row[2].strip())

                loc = shipment_locations.get(shipment_id, {'origin': 'Unknown', 'destination': 'Unknown'})

                # Insert Shipment record
                cursor.execute("""
                    INSERT OR IGNORE INTO shipment (shipment_id, origin, destination)
                    VALUES (?, ?, ?)
                """, (shipment_id, loc['origin'], loc['destination']))

                # Insert Shipment Items
                cursor.execute("""
                    INSERT INTO shipment_item (shipment_id, product_name, quantity)
                    VALUES (?, ?, ?)
                """, (shipment_id, product_name, quantity))

    conn.commit()
    conn.close()
    print("Database populated successfully!")

if __name__ == "__main__":
    populate_database("shipping.db", "shipping_data_0.csv", "shipping_data_1.csv", "shipping_data_2.csv")