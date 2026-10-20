# CricStats: Cricket Information and Analysis System

A Spring Boot REST API that stores scorecards from the first [15] matches of IPL 2026
and calculates player and team statistics from them: Orange Cap, Purple Cap, batting and
bowling averages and strike rates, most sixes, fours, 50s and 100s, MVP, and the points
table with net run rate.

Built as a Java course mini project by Lakshminkanth R, 2025103576 and Sanjay C, 2025103585.

## Tech stack
- Java 21, Maven
- Spring Boot (Web, Data JPA, Validation)
- H2 database (file mode, so data survives restarts)
- Hibernate / JPA for the data layer

## Project structure
```
com.example.cricstats
 ├─ config/       DataLoader (loads the match data on first start)
 ├─ controller/   REST endpoints
 ├─ service/      StatsService (all cricket calculations)
 ├─ repository/   Spring Data JPA repositories
 ├─ model/        JPA entities (Team, Player, IplMatch, BattingPerformance, BowlingPerformance)
 ├─ dto/          Result records returned by the stats endpoints
 └─ exception/    Custom exceptions and the global error handler
```

## Prerequisites
- JDK 21 installed
- (Optional) IntelliJ IDEA, Postman

## How to build and run
1. Clone the repository:
   `git clone [your repository URL]` and open the folder.
2. Run the app, either:
    - in IntelliJ: open `CricStatsApplication` and click Run, or
    - from a terminal in the project folder:
        - Windows: `mvnw.cmd spring-boot:run`
        - Mac/Linux: `./mvnw spring-boot:run`
3. Wait for `Started CricStatsApplication`. The API is now at `http://localhost:8080`.

## Database configuration
Set in `src/main/resources/application.properties`:
- `spring.datasource.url=jdbc:h2:file:./data/cricstats` (file database in the `data/` folder)
- User `sa`, empty password
- Tables are created automatically by Hibernate (`ddl-auto=update`)

H2 console: `http://localhost:8080/h2-console`
(JDBC URL `jdbc:h2:file:./data/cricstats`, user `sa`, no password).

## Loading the data
On the first start, `DataLoader` (in `config/`) inserts all teams, players, matches and
scorecards. If the database already has data, it skips loading.
To reload from scratch: stop the app, delete the `data/` folder, and run again.
To add matches, add lines to the `loadMatches`, `loadBatting` and `loadBowling` methods.

## Sample requests
Open in a browser (all GET):
- `http://localhost:8080/api/stats/orange-cap?limit=5`
- `http://localhost:8080/api/stats/purple-cap`
- `http://localhost:8080/api/stats/points-table`
- `http://localhost:8080/api/stats/mvp`

Create a team (POST, JSON body):
```
POST http://localhost:8080/api/teams
{"name": "Chennai Super Kings", "shortName": "CSK"}
```
Response: `201 Created` with the saved team and its id.

The full list of endpoints is in [docs/API.md](docs/API.md).
Design documents (architecture and ER diagrams) are in [docs/DESIGN.md](docs/DESIGN.md).

## Testing
Import `docs/CricStats.postman_collection.json` into Postman. It contains requests for
every kind of operation and the error cases (400, 404, 409, 422). Screenshots of results
are in `docs/screenshots/`.

## Error format
All errors return JSON, for example:
```
{"timestamp": "2026-10-20T16:00:00", "status": 404, "error": "Not Found",
 "message": "Team not found with id 999"}
```
