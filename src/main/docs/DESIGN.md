# CricStats: Design Document

## Problem statement
IPL fans and analysts want quick answers (who has the Orange Cap, what is a bowler's
economy, where does a team stand), but scorecards are scattered across matches and
manual calculation is slow and error-prone. CricStats stores the scorecard of each match
once and calculates every statistic from that single source of truth, exposing it
through a REST API.

## Layered architecture
```mermaid
flowchart LR
    Client["Client<br/>(Browser / Postman)"] --> Controller["Controller layer<br/>@RestController"]
    Controller --> Service["Service layer<br/>StatsService<br/>(cricket logic)"]
    Controller --> Repository
    Service --> Repository["Repository layer<br/>Spring Data JPA"]
    Repository --> DB[("H2 database")]
    Controller -.errors.-> Handler["GlobalExceptionHandler<br/>@RestControllerAdvice"]
    Loader["DataLoader<br/>(runs at startup)"] --> Repository
```

- **Controller:** receives HTTP requests, validates input (`@Valid`), returns JSON and status codes.
- **Service:** holds the calculations (averages, strike rates, MVP, points table, net run rate).
- **Repository:** `JpaRepository` interfaces. JPA generates the SQL.
- **Exception handler:** turns exceptions into clean JSON errors.
- Spring injects each dependency through the constructor (dependency injection).

## ER diagram
```mermaid
erDiagram
    TEAM ||--o{ PLAYER : "has"
    TEAM ||--o{ IPL_MATCH : "team1"
    TEAM ||--o{ IPL_MATCH : "team2"
    TEAM |o--o{ IPL_MATCH : "winner"
    PLAYER |o--o{ IPL_MATCH : "man of the match"
    PLAYER ||--o{ BATTING_PERFORMANCE : "bats"
    IPL_MATCH ||--o{ BATTING_PERFORMANCE : "contains"
    PLAYER ||--o{ BOWLING_PERFORMANCE : "bowls"
    IPL_MATCH ||--o{ BOWLING_PERFORMANCE : "contains"

    TEAM {
        long id PK
        string name
        string short_name
    }
    PLAYER {
        long id PK
        string name
        string role
        long team_id FK
    }
    IPL_MATCH {
        long id PK
        int match_number
        date match_date
        string venue
        long team1_id FK
        long team2_id FK
        long winner_id FK
        long man_of_match_id FK
        int team1_runs
        int team1_wickets
        int team1_balls
        int team2_runs
        int team2_wickets
        int team2_balls
        string result_text
    }
    BATTING_PERFORMANCE {
        long id PK
        long player_id FK
        long match_id FK
        int runs
        int balls_faced
        int fours
        int sixes
        boolean not_out
    }
    BOWLING_PERFORMANCE {
        long id PK
        long player_id FK
        long match_id FK
        int balls_bowled
        int runs_conceded
        int wickets
    }
```

## Key design decisions
- **Only raw scorecard data is stored.** Totals, averages, 50s and 100s are calculated, so
  nothing can get out of sync.
- **Overs are stored as balls** (3.4 overs = 22 balls) because cricket overs are not decimals.
- **Batting average = runs / dismissals.** A player never dismissed has an undefined average
  (null in leaderboards, HTTP 422 when requested directly).
- **Net run rate** counts a team that is all out as having faced its full 20 overs.
- **Points table:** 2 points per win, 1 per no result.
- **MVP formula:** 1 point per run, +1 per four, +2 per six, +10 per fifty, +25 per hundred,
  25 per wicket (constants at the top of `StatsService`).
- **Persistence:** H2 file database so data survives restarts; `DataLoader` fills it on first start.