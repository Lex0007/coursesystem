# Dokumentation der Klassen

## **1. Assert**
Die Klasse `Assert` bietet Hilfsfunktionen zur Überprüfung von Eingaben und stellt sicher, dass bestimmte Bedingungen erfüllt sind, bevor der Code ausgeführt wird.

### **Methoden**
- **`notNull(Object o)`**
    - Wirft eine `IllegalArgumentException`, wenn das übergebene Objekt `null` ist.
    - Verhindert, dass mit `null`-Werten gearbeitet wird.

## **2. DatabaseException**
Die `DatabaseException` ist eine benutzerdefinierte Klasse für Datenbankfehler. Sie sorgt für klarere Fehlermeldungen bei Datenbankproblemen.

### **Konstruktoren**
- **`DatabaseException(String message)`**
    - Erstellt eine Exception mit einer spezifischen Fehlermeldung.

## **3. MysqlDatabaseConnection**
Diese Klasse verwaltet die Verbindung zur MySQL-Datenbank und verwendet das Singleton-Muster, um sicherzustellen, dass es nur eine aktive Verbindung gibt.

### **Methoden**
- **`getConnection(String url, String user, String pwd)`**
    - Stellt eine Verbindung zur Datenbank her oder gibt eine bestehende Verbindung zurück.

## **4. BaseRepository<T, I>**
Ein Interface für grundlegende CRUD-Operationen (Erstellen, Lesen, Aktualisieren, Löschen) in der Datenzugriffslogik.

### **Methoden**
- **`insert(T entity)`**: Fügt ein neues Objekt in die Datenbank ein.
- **`getById(I id)`**: Ruft ein Objekt anhand der ID ab.
- **`getAll()`**: Gibt alle Objekte aus der Datenbank zurück.
- **`update(T entity)`**: Aktualisiert ein vorhandenes Objekt.
- **`deleteById(I id)`**: Löscht ein Objekt anhand der ID.

## **5. MyCourseRepository**
Eine spezialisierte Version des `BaseRepository`-Interfaces für Kursdaten. Es erweitert die CRUD-Methoden um spezifische Suchfunktionen für Kurse.

### **Methoden**
- **`findAllByCourseName(String name)`**: Sucht Kurse mit einem bestimmten Namen.
- **`findAllCoursesByDescription(String description)`**: Sucht Kurse mit einer bestimmten Beschreibung.
- **`findAllCoursesByNameOrDescription(String searchText)`**: Sucht Kurse, bei denen Name oder Beschreibung den Suchtext enthalten.
- **`findAllCoursesByCourseType(CourseType courseType)`**: Gibt Kurse eines bestimmten Typs zurück.
- **`findAllCoursesByStartDate(Date startDate)`**: Gibt Kurse mit einem bestimmten Startdatum zurück.
- **`findAllRunningCourses()`**: Gibt alle aktuell laufenden Kurse zurück.

## **6. MySqlCourseRepository**
Die konkrete Implementierung des `MyCourseRepository`, die mit einer MySQL-Datenbank arbeitet.

### **Felder**
- **`private Connection con`**: Die Datenbankverbindung, die für Abfragen verwendet wird.

### **Methoden**
- **CRUD-Operationen:**
    - **`insert(Course entity)`**: Fügt einen neuen Kurs in die Datenbank ein.
    - **`getById(Long id)`**: Ruft einen Kurs anhand seiner ID ab.
    - **`getAll()`**: Gibt alle Kurse aus der Datenbank zurück.
    - **`update(Course entity)`**: Aktualisiert einen Kurs.
    - **`deleteById(Long id)`**: Löscht einen Kurs nach ID und prüft vorher, ob er existiert.
- **Spezifische Methoden:**
    - **`findAllByCourseName(String name)`**: Sucht Kurse nach Namen.
    - **`findAllCoursesByDescription(String description)`**: Sucht Kurse nach Beschreibung.
    - **`findAllRunningCourses()`**: Gibt alle laufenden Kurse zurück.
    - **`countCoursesInDbWithId(Long id)`**: Zählt Kurse mit einer bestimmten ID in der Datenbank.

## **7. Course**
Die Klasse `Course` repräsentiert die Daten eines Kurses. Sie speichert Details wie Name, Beschreibung, Dauer, Start-/Enddatum und Typ.

### **Felder**
- **`private String name`**: Der Name des Kurses.
- **`private String description`**: Die Beschreibung des Kurses.
- **`private int hours`**: Die Dauer des Kurses in Stunden.
- **`private Date beginDate`**: Das Startdatum.
- **`private Date endDate`**: Das Enddatum.
- **`private CourseType courseType`**: Der Typ des Kurses (z. B. Online, Präsenz).

### **Konstruktoren**
- **`Course(Long id, String name, String description, int hours, Date beginDate, Date endDate, CourseType courseType)`**: Erstellt einen Kurs mit allen Attributen.
- **`Course(String name, String description, int hours, Date beginDate, Date endDate, CourseType courseType)`**: Erstellt einen Kurs ohne ID.

### **Methoden**
- **Getter und Setter:**
    - Validieren Eingabewerte (z. B. Name muss mindestens 2 Zeichen haben, Enddatum nach Startdatum).
- **`toString()`**: Gibt eine lesbare Darstellung des Kurses zurück.
