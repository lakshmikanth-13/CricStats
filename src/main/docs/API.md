# CricStats API

Base URL: `http://localhost:8080`. All bodies and responses are JSON.

## Teams: `/api/teams`
| Method | URL | Request body | Success response |
|---|---|---|---|
| GET | `/api/teams` | none | 200, list of teams |
| GET | `/api/teams/{id}` | none | 200, team (404 if missing) |
| POST | `/api/teams` | `{"name": "Chennai Super Kings", "shortName": "CSK"}` | 201, saved team |
| PUT | `/api/teams/{id}` | same as POST | 200, updated team |
| DELETE | `/api/teams/{id}` | none | 204 (409 if players still belong to the team) |

## Players: `/api/players`
| Method | URL | Request body | Success response |
|---|---|---|---|
| GET | `/api/players` | none | 200, list |
| GET | `/api/players/{id}` | none | 200, player (404 if missing) |
| POST | `/api/players` | `{"name": "Player Name", "role": "BATTER", "team": {"id": 1}}` | 201 |
| PUT | `/api/players/{id}` | same as POST | 200 |
| DELETE | `/api/players/{id}` | none | 204 |

`role` must be BATTER, BOWLER, ALL_ROUNDER or WICKET_KEEPER.

## Matches: `/api/matches`
| Method | URL | Request body | Success response |
|---|---|---|---|
| GET | `/api/matches` | none | 200, list |
| GET | `/api/matches/{id}` | none | 200 |
| POST | `/api/matches` | see below | 201 |
| PUT | `/api/matches/{id}` | same as POST | 200 |
| DELETE | `/api/matches/{id}` | none | 204 |

```json
{"matchNumber": 1, "matchDate": "2026-03-28", "venue": "Stadium",
 "team1": {"id": 1}, "team2": {"id": 2}, "winner": {"id": 1},
 "manOfTheMatch": {"id": 3},
 "team1Runs": 180, "team1Wickets": 5, "team1Balls": 120,
 "team2Runs": 150, "team2Wickets": 10, "team2Balls": 110,
 "resultText": "Team 1 won by 30 runs"}
```
`winner` is `null` for a match with no result. The two teams must differ.

## Batting scorecard rows: `/api/batting`
| Method | URL | Request body | Success response |
|---|---|---|---|
| GET | `/api/batting` | none | 200, all rows |
| GET | `/api/batting/player/{playerId}` | none | 200, one player's innings |
| POST | `/api/batting` | see below | 201 |
| DELETE | `/api/batting/{id}` | none | 204 |

```json
{"player": {"id": 1}, "match": {"id": 1}, "runs": 72, "ballsFaced": 45,
 "fours": 6, "sixes": 3, "notOut": false}
```

## Bowling scorecard rows: `/api/bowling`
| Method | URL | Request body | Success response |
|---|---|---|---|
| GET | `/api/bowling` | none | 200, all rows |
| GET | `/api/bowling/player/{playerId}` | none | 200 |
| POST | `/api/bowling` | see below | 201 |
| DELETE | `/api/bowling/{id}` | none | 204 |

```json
{"player": {"id": 2}, "match": {"id": 1}, "ballsBowled": 24,
 "runsConceded": 28, "wickets": 2}
```

## Statistics: `/api/stats` (all GET)
Optional query parameter `limit` (default 10) on the leaderboards.

| URL | Returns |
|---|---|
| `/batting` | every batter: runs, balls, 4s, 6s, 50s, 100s, highest score, average, strike rate |
| `/bowling` | every bowler: overs, runs, wickets, average, economy, strike rate |
| `/orange-cap` | most runs |
| `/purple-cap` | most wickets |
| `/most-sixes`, `/most-fours` | boundary leaders |
| `/most-fifties`, `/most-hundreds` | milestone leaders |
| `/batting-average` | best average (never-dismissed players excluded) |
| `/batting-strike-rate` | best strike rate (minimum 30 balls) |
| `/bowling-average`, `/bowling-strike-rate` | best (lowest) values, minimum 24 balls and 1 wicket |
| `/mvp` | MVP ranking (points formula in `StatsService`) |
| `/points-table` | played, won, lost, no result, points, net run rate |
| `/players/{id}/batting` | one player's batting line (404 if none) |
| `/players/{id}/batting-average` | one player's average (422 if never dismissed) |

## Error responses
| Status | When |
|---|---|
| 400 | validation failed, malformed JSON, invalid team or player id |
| 404 | resource not found |
| 409 | delete blocked because other records depend on it |
| 422 | statistic is undefined (e.g. batting average with 0 dismissals) |

Format: `{"timestamp": "...", "status": 404, "error": "Not Found", "message": "..."}`

A ready-to-import Postman collection is in `docs/CricStats.postman_collection.json`.