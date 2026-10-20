
package com.example.cricstats.config;

import com.example.cricstats.model.*;
import com.example.cricstats.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataLoader implements CommandLineRunner {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final IplMatchRepository matchRepository;
    private final BattingPerformanceRepository battingRepository;
    private final BowlingPerformanceRepository bowlingRepository;

    // lookup tables so you can type names instead of ids
    private final Map<String, Team> teams = new HashMap<>();
    private final Map<String, Player> players = new HashMap<>();
    private final Map<Integer, IplMatch> matches = new HashMap<>();

    public DataLoader(TeamRepository teamRepository,
                      PlayerRepository playerRepository,
                      IplMatchRepository matchRepository,
                      BattingPerformanceRepository battingRepository,
                      BowlingPerformanceRepository bowlingRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.battingRepository = battingRepository;
        this.bowlingRepository = bowlingRepository;
    }

    @Override
    public void run(String... args) {
        if (teamRepository.count() > 0) {
            System.out.println("DataLoader: data already present, skipping.");
            return;
        }
        loadTeams();
        loadPlayers();
        loadMatches();
        loadBatting();
        loadBowling();
        System.out.println("DataLoader: data loaded.");
    }

    // ================= YOUR DATA GOES IN THESE 5 METHODS =================

    private void loadTeams() {
        addTeam("Mumbai Indians", "MI");
        addTeam("Kolkata Knight Riders", "KKR");
        addTeam("Rajasthan Royals", "RR");
        addTeam("Chennai Super Kings", "CSK");
        addTeam("Punjab Kings", "PBKS");
        addTeam("Gujarat Titans", "GT");
        addTeam("Lucknow Super Giants", "LSG");
        addTeam("Delhi Capitals", "DC");
        addTeam("Sunrisers Hyderabad", "SRH");
        addTeam("Royal Challengers Bengaluru", "RCB");
    }

    private void loadPlayers() {
        // ---- MI ----
        addPlayer("Ryan Rickelton",        "WICKET_KEEPER", "MI");
        addPlayer("Rohit Sharma",          "BATTER",        "MI");
        addPlayer("Suryakumar Yadav",      "BATTER",        "MI");
        addPlayer("Tilak Varma",           "BATTER",        "MI");
        addPlayer("Hardik Pandya",         "ALL_ROUNDER",   "MI");
        addPlayer("Naman Dhir",            "BATTER",        "MI");
        addPlayer("Sherfane Rutherford",   "BATTER",        "MI");
        addPlayer("Mitchell Santner",      "ALL_ROUNDER",   "MI");
        addPlayer("Corbin Bosch",          "ALL_ROUNDER",   "MI");
        addPlayer("Trent Boult",           "BOWLER",        "MI");
        addPlayer("AM Ghazanfar",          "BOWLER",        "MI");
        addPlayer("Jasprit Bumrah",        "BOWLER",        "MI");
        addPlayer("Shardul Thakur",        "BOWLER",        "MI");
        addPlayer("Mayank Markande",       "BOWLER",        "MI");
        addPlayer("Deepak Chahar",         "BOWLER",        "MI");

        // ---- KKR ----
        addPlayer("Ajinkya Rahane",        "BATTER",        "KKR");
        addPlayer("Finn Allen",            "BATTER",        "KKR");
        addPlayer("Cameron Green",         "ALL_ROUNDER",   "KKR");
        addPlayer("Angkrish Raghuvanshi",  "WICKET_KEEPER", "KKR");
        addPlayer("Rinku Singh",           "BATTER",        "KKR");
        addPlayer("Ramandeep Singh",       "ALL_ROUNDER",   "KKR");
        addPlayer("Anukul Roy",            "ALL_ROUNDER",   "KKR");
        addPlayer("Sunil Narine",          "ALL_ROUNDER",   "KKR");
        addPlayer("Varun Chakaravarthy",   "BOWLER",        "KKR");
        addPlayer("Vaibhav Arora",         "BOWLER",        "KKR");
        addPlayer("Blessing Muzarabani",   "BOWLER",        "KKR");
        addPlayer("Kartik Tyagi",          "BOWLER",        "KKR");

        // ---- RR ----
        addPlayer("Yashasvi Jaiswal",      "BATTER",        "RR");
        addPlayer("Vaibhav Sooryavanshi",  "BATTER",        "RR");
        addPlayer("Dhruv Jurel",           "WICKET_KEEPER", "RR");
        addPlayer("Riyan Parag",           "ALL_ROUNDER",   "RR");
        addPlayer("Shimron Hetmyer",       "BATTER",        "RR");
        addPlayer("Donovan Ferreira",      "BATTER",        "RR");
        addPlayer("Ravindra Jadeja",       "ALL_ROUNDER",   "RR");
        addPlayer("Jofra Archer",          "BOWLER",        "RR");
        addPlayer("Nandre Burger",         "BOWLER",        "RR");
        addPlayer("Sandeep Sharma",        "BOWLER",        "RR");
        addPlayer("Ravi Bishnoi",          "BOWLER",        "RR");
        addPlayer("Brijesh Sharma",        "BOWLER",        "RR");
        addPlayer("Tushar Deshpande",      "BOWLER",        "RR");

        // ---- CSK ----
        addPlayer("Sanju Samson",          "WICKET_KEEPER", "CSK");
        addPlayer("Ruturaj Gaikwad",       "BATTER",        "CSK");
        addPlayer("Ayush Mhatre",          "BATTER",        "CSK");
        addPlayer("Matthew Short",         "ALL_ROUNDER",   "CSK");
        addPlayer("Sarfaraz Khan",         "BATTER",        "CSK");
        addPlayer("Kartik Sharma",         "BATTER",        "CSK");
        addPlayer("Shivam Dube",           "ALL_ROUNDER",   "CSK");
        addPlayer("Jamie Overton",         "ALL_ROUNDER",   "CSK");
        addPlayer("Prashant Veer",         "ALL_ROUNDER",   "CSK");
        addPlayer("Noor Ahmad",            "BOWLER",        "CSK");
        addPlayer("Matt Henry",            "BOWLER",        "CSK");
        addPlayer("Anshul Kamboj",         "BOWLER",        "CSK");
        addPlayer("Khaleel Ahmed",         "BOWLER",        "CSK");
        addPlayer("Rahul Chahar",          "BOWLER",        "CSK");

        // ---- PBKS ----
        addPlayer("Priyansh Arya",         "BATTER",        "PBKS");
        addPlayer("Prabhsimran Singh",     "WICKET_KEEPER", "PBKS");
        addPlayer("Cooper Connolly",       "ALL_ROUNDER",   "PBKS");
        addPlayer("Shreyas Iyer",          "BATTER",        "PBKS");
        addPlayer("Nehal Wadhera",         "BATTER",        "PBKS");
        addPlayer("Shashank Singh",        "BATTER",        "PBKS");
        addPlayer("Marcus Stoinis",        "ALL_ROUNDER",   "PBKS");
        addPlayer("Marco Jansen",          "ALL_ROUNDER",   "PBKS");
        addPlayer("Xavier Bartlett",       "BOWLER",        "PBKS");
        addPlayer("Vijaykumar Vyshak",     "BOWLER",        "PBKS");
        addPlayer("Arshdeep Singh",        "BOWLER",        "PBKS");
        addPlayer("Yuzvendra Chahal",      "BOWLER",        "PBKS");

        // ---- GT ----
        addPlayer("Sai Sudharsan",         "BATTER",        "GT");
        addPlayer("Shubman Gill",          "BATTER",        "GT");
        addPlayer("Jos Buttler",           "WICKET_KEEPER", "GT");
        addPlayer("Glenn Phillips",        "ALL_ROUNDER",   "GT");
        addPlayer("Washington Sundar",     "ALL_ROUNDER",   "GT");
        addPlayer("Shahrukh Khan",         "BATTER",        "GT");
        addPlayer("Rahul Tewatia",         "ALL_ROUNDER",   "GT");
        addPlayer("Rashid Khan",           "ALL_ROUNDER",   "GT");
        addPlayer("Kumar Kushagra",        "WICKET_KEEPER", "GT");
        addPlayer("Kagiso Rabada",         "BOWLER",        "GT");
        addPlayer("Ashok Sharma",          "BOWLER",        "GT");
        addPlayer("Mohammed Siraj",        "BOWLER",        "GT");
        addPlayer("Prasidh Krishna",       "BOWLER",        "GT");

        // ---- LSG ----
        addPlayer("Mitchell Marsh",        "ALL_ROUNDER",   "LSG");
        addPlayer("Rishabh Pant",          "WICKET_KEEPER", "LSG");
        addPlayer("Aiden Markram",         "BATTER",        "LSG");
        addPlayer("Ayush Badoni",          "BATTER",        "LSG");
        addPlayer("Nicholas Pooran",       "BATTER",        "LSG");
        addPlayer("Abdul Samad",           "BATTER",        "LSG");
        addPlayer("Mukul Choudhary",       "BATTER",        "LSG");
        addPlayer("Shahbaz Ahmed",         "ALL_ROUNDER",   "LSG");
        addPlayer("Mohammed Shami",        "BOWLER",        "LSG");
        addPlayer("Anrich Nortje",         "BOWLER",        "LSG");
        addPlayer("Mohsin Khan",           "BOWLER",        "LSG");
        addPlayer("Prince Yadav",          "BOWLER",        "LSG");
        addPlayer("Digvesh Singh Rathi",   "BOWLER",        "LSG");
        addPlayer("Manimaran Siddharth",   "BOWLER",        "LSG");
        addPlayer("Avesh Khan",            "BOWLER",        "LSG");

        // ---- DC ----
        addPlayer("KL Rahul",              "WICKET_KEEPER", "DC");
        addPlayer("Pathum Nissanka",       "BATTER",        "DC");
        addPlayer("Nitish Rana",           "BATTER",        "DC");
        addPlayer("Sameer Rizvi",          "BATTER",        "DC");
        addPlayer("Axar Patel",            "ALL_ROUNDER",   "DC");
        addPlayer("Tristan Stubbs",        "BATTER",        "DC");
        addPlayer("David Miller",          "BATTER",        "DC");
        addPlayer("Vipraj Nigam",          "ALL_ROUNDER",   "DC");
        addPlayer("Mukesh Kumar",          "BOWLER",        "DC");
        addPlayer("Lungi Ngidi",           "BOWLER",        "DC");
        addPlayer("T Natarajan",           "BOWLER",        "DC");
        addPlayer("Kuldeep Yadav",         "BOWLER",        "DC");

        // ---- SRH ----
        addPlayer("Travis Head",           "BATTER",        "SRH");
        addPlayer("Abhishek Sharma",       "ALL_ROUNDER",   "SRH");
        addPlayer("Ishan Kishan",          "WICKET_KEEPER", "SRH");
        addPlayer("Heinrich Klaasen",      "WICKET_KEEPER", "SRH");
        addPlayer("Liam Livingstone",      "ALL_ROUNDER",   "SRH");
        addPlayer("Aniket Verma",          "BATTER",        "SRH");
        addPlayer("Nitish Kumar Reddy",    "ALL_ROUNDER",   "SRH");
        addPlayer("Salil Arora",           "BATTER",        "SRH");
        addPlayer("Harsh Dubey",           "ALL_ROUNDER",   "SRH");
        addPlayer("Shivang Kumar",         "ALL_ROUNDER",   "SRH");
        addPlayer("Harshal Patel",         "BOWLER",        "SRH");
        addPlayer("David Payne",           "BOWLER",        "SRH");
        addPlayer("Jaydev Unadkat",        "BOWLER",        "SRH");
        addPlayer("Eshan Malinga",         "BOWLER",        "SRH");

        // ---- RCB ----
        addPlayer("Philip Salt",           "WICKET_KEEPER", "RCB");
        addPlayer("Virat Kohli",           "BATTER",        "RCB");
        addPlayer("Devdutt Padikkal",      "BATTER",        "RCB");
        addPlayer("Rajat Patidar",         "BATTER",        "RCB");
        addPlayer("Tim David",             "BATTER",        "RCB");
        addPlayer("Krunal Pandya",         "ALL_ROUNDER",   "RCB");
        addPlayer("Romario Shepherd",      "ALL_ROUNDER",   "RCB");
        addPlayer("Jacob Duffy",           "BOWLER",        "RCB");
        addPlayer("Bhuvneshwar Kumar",     "BOWLER",        "RCB");
        addPlayer("Abhinandan Singh",      "BOWLER",        "RCB");
        addPlayer("Suyash Sharma",         "BOWLER",        "RCB");
        addPlayer("Jitesh Sharma",      "WICKET_KEEPER", "RCB");
    }

    private void loadMatches() {
        // addMatch(matchNo, "yyyy-mm-dd", "Venue", "TEAM1", "TEAM2", "WINNER" (or null),
        //          "Man of the match name" (or null),
        //          team1Runs, team1Wickets, "team1Overs",
        //          team2Runs, team2Wickets, "team2Overs",
        //          "Result text");
        addMatch(1, "2026-03-28", "M.Chinnaswamy Stadium", "RCB", "SRH", "RCB",
                "Jacob Duffy",
                201, 9, "20",
                203, 4, "15.4",
                "RCB won by 6 wickets");   // EXAMPLE - replace
        addMatch(2, "2026-03-29", "Eden Gardens", "MI", "KKR",
                "MI", "Shardul Thakur",
                224, 4, "19.1",
                220, 4, "20",
                "MI won by 6 wkts");

        addMatch(3, "2026-03-30", "Guwhati", "RR", "CSK",
                "RR", "Nandre Burger",
                128, 2, "12.1",
                127, 10, "19.4",
                "RR won by 8 wkts");

        addMatch(4, "2026-03-31", "New Chandigarh", "PBKS", "GT",
                "PBKS", "Cooper Connolly",
                165, 7, "19.1",
                162, 6, "20",
                "PBKS won by 3 wkts");

        addMatch(5, "2026-04-01", "Ekana Stadium", "LSG", "DC",
                "DC", "Sameer Rizvi",
                141, 10, "18.4",
                145, 4, "17.1",
                "DC won by 6 wkts");

        addMatch(6, "2026-04-01", "Rajiv Gandhi Stadium", "KKR", "SRH",
                "SRH", "Nitish Kumar Reddy",
                161, 10, "16",
                226, 8, "20",
                "SRH won by 65 runs");

        // Match 7 (CSK vs PBKS): no result header / man of the match in the PDF
        addMatch(7, "2026-04-02", "M.A Chidambaram Stadium", "CSK", "PBKS",
                "PBKS", null,
                209, 5, "20",
                210, 5, "18.4",
                "PBKS won by 5 wkts");

        addMatch(8, "2026-04-03", "Wankhade Stadium", "DC", "MI",
                "DC", "Sameer Rizvi",
                164, 4, "18.1",
                162, 6, "20",
                "DC won by 6 wkts");

        addMatch(9, "2026-04-04", "Narendra Modi  Stadium", "GT", "RR",
                "RR", "Ravi Bishnoi",
                204, 8, "20",
                210, 6, "20",
                "RR won by 6 runs");

        addMatch(10, "2026-04-05", "Ekana Stadium", "SRH", "LSG",
                "LSG", "Mohammed Shami",
                156, 9, "20",
                160, 5, "19.5",
                "LSG won by 5 wkts");

        addMatch(11, "2026-04-05", "M.Chinnaswamy Stadium", "RCB", "CSK",
                "RCB", "Tim David",
                250, 3, "20",
                207, 10, "19.4",
                "RCB won by 43 runs");
    }

    private void loadBatting() {
        // matchNo, player, runs, balls, fours, sixes, notOut
        addBat(1, "Travis Head",          11,  9, 2, 0, false);
        addBat(1, "Abhishek Sharma",      7,  8, 0, 1, false);
        addBat(1, "Ishan Kishan",         80,  38, 8, 5, false);
        addBat(1, "Salil Arora",          9, 6, 0, 1, false);
        addBat(1, "Heinrich Klaasen",    31, 22, 2, 1, false);
        addBat(1, "Nitish Kumar Reddy",  1, 6, 0, 0, false);
        addBat(1, "Aniket Verma",         43,  18, 3, 4, true);
        addBat(1, "Harsh Dubey",          3,  3, 0, 0, false);
        addBat(1, "David Payne",        6,  5, 0, 0, true);
        addBat(1, "Jaydev Unadkat",        4,  3, 0, 0, true);
        addBat(1, "Harshal Patel",        0,  2, 0, 0, false);

        addBat(1, "Philip Salt",         8, 7, 2, 0, false);
        addBat(1, "Virat Kohli",         69, 38, 5, 5, true);
        addBat(1, "Devdutt Padikkal",    61, 26, 7, 4, false);
        addBat(1, "Rajat Patidar",       31, 12, 2, 3, false);
        addBat(1, "Jitesh Sharma",       0, 1, 0, 0, false);
        addBat(1, "Tim David",           16, 10, 1, 1, true);
        // ===== Match 2: MI vs KKR =====
        // KKR innings
        addBat(2, "Ajinkya Rahane",       67, 40, 3, 5, false);
        addBat(2, "Finn Allen",           37, 17, 6, 2, false);
        addBat(2, "Cameron Green",        18, 10, 1, 1, false);
        addBat(2, "Angkrish Raghuvanshi", 51, 29, 6, 2, false);
        addBat(2, "Rinku Singh",          33, 21, 4, 0, true);
        addBat(2, "Ramandeep Singh",       4,  4, 0, 0, true);
        // MI innings
        addBat(2, "Ryan Rickelton",       81, 43, 4, 8, false);
        addBat(2, "Rohit Sharma",         78, 38, 6, 6, false);
        addBat(2, "Suryakumar Yadav",     16,  8, 3, 0, false);
        addBat(2, "Tilak Varma",          20, 14, 4, 0, false);
        addBat(2, "Hardik Pandya",        18, 11, 3, 0, true);
        addBat(2, "Naman Dhir",            5,  2, 1, 0, true);

        // ===== Match 3: RR vs CSK =====
        // CSK innings
        addBat(3, "Sanju Samson",          6,  7, 1, 0, false);
        addBat(3, "Ruturaj Gaikwad",       6, 11, 1, 0, false);
        addBat(3, "Ayush Mhatre",          0,  1, 0, 0, false);
        addBat(3, "Matthew Short",         2,  7, 0, 0, false);
        addBat(3, "Sarfaraz Khan",        17, 12, 2, 1, false);
        addBat(3, "Kartik Sharma",        18, 15, 0, 1, false);
        addBat(3, "Shivam Dube",           6,  4, 0, 1, false);
        addBat(3, "Jamie Overton",        43, 36, 2, 2, false);
        addBat(3, "Noor Ahmad",            1,  9, 0, 0, false);
        addBat(3, "Matt Henry",            5,  7, 1, 0, false);
        addBat(3, "Anshul Kamboj",         7, 10, 1, 0, true);
        // RR innings
        addBat(3, "Yashasvi Jaiswal",     38, 36, 3, 1, true);
        addBat(3, "Vaibhav Sooryavanshi", 52, 17, 4, 5, false);
        addBat(3, "Dhruv Jurel",          18,  9, 4, 0, false);
        addBat(3, "Riyan Parag",          14, 11, 1, 1, true);

        // ===== Match 4: PBKS vs GT =====
        // GT innings
        addBat(4, "Sai Sudharsan",        13, 11, 2, 0, false);
        addBat(4, "Shubman Gill",         39, 27, 6, 0, false);
        addBat(4, "Jos Buttler",          38, 33, 3, 2, false);
        addBat(4, "Glenn Phillips",       25, 17, 1, 1, false);
        addBat(4, "Washington Sundar",    18, 16, 2, 0, false);
        addBat(4, "Shahrukh Khan",         4,  6, 0, 0, false);
        addBat(4, "Rahul Tewatia",        11, 10, 1, 0, true);
        addBat(4, "Rashid Khan",           0,  1, 0, 0, true);
        // PBKS innings
        addBat(4, "Priyansh Arya",         7,  8, 0, 1, false);
        addBat(4, "Prabhsimran Singh",    37, 24, 1, 4, false);
        addBat(4, "Cooper Connolly",      72, 44, 5, 5, true);
        addBat(4, "Shreyas Iyer",         18, 11, 0, 2, false);
        addBat(4, "Nehal Wadhera",         3,  6, 0, 0, false);
        addBat(4, "Shashank Singh",        4,  5, 0, 0, false);
        addBat(4, "Marcus Stoinis",        0,  2, 0, 0, false);
        addBat(4, "Marco Jansen",          9, 10, 0, 1, false);
        addBat(4, "Xavier Bartlett",      11,  5, 0, 1, true);

        // ===== Match 5: LSG vs DC =====
        // LSG innings
        addBat(5, "Mitchell Marsh",       35, 28, 2, 3, false);
        addBat(5, "Rishabh Pant",          7,  9, 1, 0, false);
        addBat(5, "Aiden Markram",        11,  8, 1, 1, false);
        addBat(5, "Ayush Badoni",          0,  3, 0, 0, false);
        addBat(5, "Nicholas Pooran",       8,  8, 1, 0, false);
        addBat(5, "Abdul Samad",          36, 25, 3, 1, false);
        addBat(5, "Mukul Choudhary",      14, 11, 2, 0, false);
        addBat(5, "Shahbaz Ahmed",        15, 16, 1, 0, true);
        addBat(5, "Mohammed Shami",        1,  2, 0, 0, false);
        addBat(5, "Anrich Nortje",         0,  1, 0, 0, false);
        addBat(5, "Mohsin Khan",           0,  1, 0, 0, false);
        // DC innings
        addBat(5, "KL Rahul",              0,  1, 0, 0, false);
        addBat(5, "Pathum Nissanka",       1,  5, 0, 0, false);
        addBat(5, "Nitish Rana",          15, 17, 2, 1, false);
        addBat(5, "Sameer Rizvi",         70, 47, 5, 4, true);
        addBat(5, "Axar Patel",            0,  1, 0, 0, false);
        addBat(5, "Tristan Stubbs",       39, 32, 3, 1, true);

        // ===== Match 6: KKR vs SRH =====
        // SRH innings
        addBat(6, "Travis Head",          46, 21, 6, 3, false);
        addBat(6, "Abhishek Sharma",      48, 21, 4, 4, false);
        addBat(6, "Ishan Kishan",         14,  9, 3, 0, false);
        addBat(6, "Heinrich Klaasen",     52, 35, 4, 1, false);
        addBat(6, "Aniket Verma",          1,  4, 0, 0, false);
        addBat(6, "Nitish Kumar Reddy",   39, 24, 4, 1, false);
        addBat(6, "Salil Arora",           0,  1, 0, 0, false);
        addBat(6, "Harsh Dubey",           9,  3, 2, 0, true);
        addBat(6, "Shivang Kumar",         4,  2, 1, 0, false);
        // KKR innings
        addBat(6, "Ajinkya Rahane",        8, 10, 0, 0, false);
        addBat(6, "Finn Allen",           28,  7, 4, 2, false);
        addBat(6, "Angkrish Raghuvanshi", 52, 29, 6, 2, false);
        addBat(6, "Cameron Green",         2,  2, 0, 0, false);
        addBat(6, "Rinku Singh",          35, 25, 4, 1, false);
        addBat(6, "Anukul Roy",            0,  2, 0, 0, false);
        addBat(6, "Ramandeep Singh",      10,  9, 1, 0, false);
        addBat(6, "Sunil Narine",         12,  4, 0, 2, false);
        addBat(6, "Kartik Tyagi",          5,  5, 1, 0, false);
        addBat(6, "Vaibhav Arora",         1,  2, 0, 0, true);
        addBat(6, "Varun Chakaravarthy",   0,  1, 0, 0, false);

        // ===== Match 7: CSK vs PBKS =====
        // CSK innings
        addBat(7, "Sanju Samson",          7,  7, 1, 0, false);
        addBat(7, "Ruturaj Gaikwad",      28, 22, 2, 0, false);
        addBat(7, "Ayush Mhatre",         73, 43, 6, 5, false);
        addBat(7, "Shivam Dube",          45, 27, 5, 1, true);
        addBat(7, "Kartik Sharma",         1,  3, 0, 0, false);
        addBat(7, "Sarfaraz Khan",        32, 12, 6, 1, false);
        addBat(7, "Prashant Veer",         6,  7, 1, 0, true);
        // PBKS innings
        addBat(7, "Priyansh Arya",        39, 11, 3, 4, false);
        addBat(7, "Prabhsimran Singh",    43, 34, 6, 1, false);
        addBat(7, "Cooper Connolly",      36, 22, 6, 0, false);
        addBat(7, "Shreyas Iyer",         50, 29, 4, 3, false);
        addBat(7, "Nehal Wadhera",        10,  9, 0, 1, false);
        addBat(7, "Shashank Singh",       14,  6, 2, 0, true);
        addBat(7, "Marcus Stoinis",        9,  3, 2, 0, true);

        // ===== Match 8: DC vs MI =====
        // MI innings
        addBat(8, "Ryan Rickelton",        9, 11, 2, 0, false);
        addBat(8, "Rohit Sharma",         35, 26, 5, 1, false);
        addBat(8, "Tilak Varma",           0,  2, 0, 0, false);
        addBat(8, "Suryakumar Yadav",     51, 36, 3, 2, false);
        addBat(8, "Sherfane Rutherford",   5,  7, 1, 0, false);
        addBat(8, "Naman Dhir",           28, 21, 2, 1, false);
        addBat(8, "Mitchell Santner",     18, 13, 2, 0, true);
        addBat(8, "Corbin Bosch",         11,  4, 2, 0, true);
        // DC innings
        addBat(8, "KL Rahul",              1,  3, 0, 0, false);
        addBat(8, "Pathum Nissanka",      44, 30, 6, 1, false);
        addBat(8, "Nitish Rana",           0,  3, 0, 0, false);
        addBat(8, "Sameer Rizvi",         90, 51, 7, 7, false);
        addBat(8, "David Miller",         21, 18, 4, 0, true);
        addBat(8, "Tristan Stubbs",        3,  4, 0, 0, true);

        // ===== Match 9: GT vs RR =====
        // RR innings
        addBat(9, "Yashasvi Jaiswal",     55, 36, 6, 3, false);
        addBat(9, "Vaibhav Sooryavanshi", 31, 18, 5, 1, false);
        addBat(9, "Dhruv Jurel",          75, 42, 5, 5, false);
        addBat(9, "Riyan Parag",           8,  4, 0, 1, false);
        addBat(9, "Shimron Hetmyer",      18,  8, 1, 2, false);
        addBat(9, "Donovan Ferreira",      1,  3, 0, 0, false);
        addBat(9, "Ravindra Jadeja",       7,  7, 1, 0, true);
        addBat(9, "Jofra Archer",          1,  2, 0, 0, true);
        // GT innings
        addBat(9, "Sai Sudharsan",        73, 44, 9, 3, false);
        addBat(9, "Kumar Kushagra",       18, 14, 3, 0, false);
        addBat(9, "Jos Buttler",          26, 14, 5, 0, false);
        addBat(9, "Glenn Phillips",        3,  4, 0, 0, false);
        addBat(9, "Washington Sundar",     4,  2, 1, 0, false);
        addBat(9, "Rahul Tewatia",        12,  6, 1, 1, false);
        addBat(9, "Shahrukh Khan",        11,  4, 1, 1, false);
        // TODO: Rashid Khan's line was cut off in the screenshot. Runs (24) are worked out
        // from the team total (204); balls/fours/sixes are unknown - please fill in.
        addBat(9, "Rashid Khan",          24,  16, 0, 0, false);
        addBat(9, "Kagiso Rabada",        23, 16, 1, 2, true);
        addBat(9, "Ashok Sharma",          0,  1, 0, 0, true);

        // ===== Match 10: SRH vs LSG =====
        // SRH innings
        addBat(10, "Travis Head",          7,  8, 1, 0, false);
        addBat(10, "Abhishek Sharma",      0,  2, 0, 0, false);
        addBat(10, "Ishan Kishan",         1,  4, 0, 0, false);
        addBat(10, "Liam Livingstone",    14, 20, 0, 1, false);
        addBat(10, "Heinrich Klaasen",    62, 41, 5, 2, false);
        addBat(10, "Nitish Kumar Reddy",  56, 33, 3, 5, false);
        addBat(10, "Aniket Verma",         2,  5, 0, 0, true);
        addBat(10, "Harsh Dubey",          0,  1, 0, 0, false);
        addBat(10, "Shivang Kumar",        5,  4, 1, 0, false);
        addBat(10, "Harshal Patel",        4,  2, 1, 0, false);
        // LSG innings
        addBat(10, "Aiden Markram",       45, 27, 6, 2, false);
        addBat(10, "Mitchell Marsh",      14, 12, 2, 0, false);
        addBat(10, "Rishabh Pant",        68, 50, 9, 0, true);
        addBat(10, "Ayush Badoni",        12,  9, 1, 0, false);
        addBat(10, "Nicholas Pooran",      1,  4, 0, 0, false);
        addBat(10, "Abdul Samad",         16, 12, 2, 0, false);
        addBat(10, "Mukul Choudhary",      2,  5, 0, 0, true);

        // ===== Match 11: RCB vs CSK =====
        // RCB innings
        addBat(11, "Philip Salt",         46, 30, 3, 2, false);
        addBat(11, "Virat Kohli",         28, 18, 2, 1, false);
        addBat(11, "Devdutt Padikkal",    50, 29, 5, 2, false);
        addBat(11, "Rajat Patidar",       48, 19, 1, 6, true);
        addBat(11, "Tim David",           70, 25, 3, 8, true);
        // CSK innings
        addBat(11, "Sanju Samson",         9,  5, 0, 1, false);
        addBat(11, "Ruturaj Gaikwad",      7,  3, 0, 1, false);
        addBat(11, "Ayush Mhatre",         1,  3, 0, 0, false);
        addBat(11, "Sarfaraz Khan",       50, 25, 8, 2, false);
        addBat(11, "Kartik Sharma",        6,  3, 0, 1, false);
        addBat(11, "Shivam Dube",         18, 13, 2, 1, false);
        addBat(11, "Prashant Veer",       43, 29, 6, 1, false);
        addBat(11, "Jamie Overton",       37, 16, 4, 2, false);
        addBat(11, "Anshul Kamboj",       19, 12, 1, 1, true);
        addBat(11, "Noor Ahmad",           8,  8, 0, 1, false);
        addBat(11, "Matt Henry",           2,  3, 0, 0, false);
    }

    private void loadBowling() {
        // matchNo, player, overs, runsConceded, wickets
        addBowl(1, "Jacob Duffy",          "4",   22, 3);
        addBowl(1, "Bhuvneshwar Kumar",    "4",   31, 1);
        addBowl(1, "Abhinandan Singh",     "3", 38, 1);
        addBowl(1, "Krunal Pandya",        "2",   26, 0);
        addBowl(1, "Suyash Sharma",        "3",   28, 1);
        addBowl(1, "Romario Shepherd",     "4",   54, 3);
        addBowl(1, "David Payne",           "3",   35, 2);
        addBowl(1, "Harsh Dubey",           "3",   35, 1);
        addBowl(1, "Jaydev Unadkat",        "3",   29, 1);
        addBowl(1, "Eshan Malinga",         "2",   35, 0);
        addBowl(1, "Harshal Patel",         "2.4",   39, 0);
        addBowl(1, "Nitish Kumar Reddy",    "2",   19, 0);

        // ===== Match 2: MI vs KKR =====
        // MI bowlers (vs KKR)
        addBowl(2, "Trent Boult",           "4",   38, 0);
        addBowl(2, "Hardik Pandya",         "3",   39, 1);
        addBowl(2, "AM Ghazanfar",          "4",   51, 0);
        addBowl(2, "Jasprit Bumrah",        "4",   35, 0);
        addBowl(2, "Shardul Thakur",        "4",   39, 3);
        addBowl(2, "Mayank Markande",       "1",   16, 0);
        // KKR bowlers (vs MI)
        addBowl(2, "Vaibhav Arora",         "4",   52, 1);
        addBowl(2, "Blessing Muzarabani",   "3",   34, 0);
        addBowl(2, "Varun Chakaravarthy",   "4",   48, 0);
        addBowl(2, "Kartik Tyagi",          "4",   43, 1);
        addBowl(2, "Sunil Narine",          "3",   30, 1);
        addBowl(2, "Anukul Roy",            "1.1", 15, 0);

        // ===== Match 3: RR vs CSK =====
        // RR bowlers (vs CSK)
        addBowl(3, "Jofra Archer",          "4",   19, 2);
        addBowl(3, "Nandre Burger",         "4",   26, 2);
        addBowl(3, "Brijesh Sharma",        "3",   17, 1);
        addBowl(3, "Sandeep Sharma",        "2.4", 22, 1);
        addBowl(3, "Ravi Bishnoi",          "3",   16, 1);
        addBowl(3, "Ravindra Jadeja",       "3",   18, 2);
        // CSK bowlers (vs RR)
        addBowl(3, "Matt Henry",            "3",   40, 0);
        addBowl(3, "Khaleel Ahmed",         "3",   17, 0);
        addBowl(3, "Anshul Kamboj",         "3",   27, 2);
        addBowl(3, "Noor Ahmad",            "2",   24, 0);
        addBowl(3, "Jamie Overton",         "1",   14, 0);
        addBowl(3, "Matthew Short",         "0.1",  1, 0);

        // ===== Match 4: PBKS vs GT =====
        // PBKS bowlers (vs GT)
        addBowl(4, "Arshdeep Singh",        "4",   42, 0);
        addBowl(4, "Xavier Bartlett",       "4",   36, 0);
        addBowl(4, "Marco Jansen",          "4",   20, 1);
        addBowl(4, "Vijaykumar Vyshak",     "4",   34, 3);
        addBowl(4, "Yuzvendra Chahal",      "4",   28, 2);
        // GT bowlers (vs PBKS)
        addBowl(4, "Mohammed Siraj",        "2",   15, 0);
        addBowl(4, "Kagiso Rabada",         "3",   34, 1);
        addBowl(4, "Ashok Sharma",          "3",   31, 1);
        addBowl(4, "Rashid Khan",           "4",   29, 1);
        addBowl(4, "Washington Sundar",     "3.1", 27, 1);
        addBowl(4, "Prasidh Krishna",       "4",   29, 3);

        // ===== Match 5: LSG vs DC =====
        // LSG bowlers (vs DC)
        addBowl(5, "Mohammed Shami",        "4",   28, 1);
        addBowl(5, "Prince Yadav",          "3",   20, 2);
        addBowl(5, "Mohsin Khan",           "4",   19, 1);
        addBowl(5, "Anrich Nortje",         "4",   39, 0);
        addBowl(5, "Shahbaz Ahmed",         "1",   16, 0);
        addBowl(5, "Aiden Markram",         "1",   13, 0);
        addBowl(5, "Abdul Samad",           "0.1",  6, 0);
        // DC bowlers (vs LSG)
        addBowl(5, "Mukesh Kumar",          "3",   17, 0);
        addBowl(5, "Lungi Ngidi",           "3.4", 27, 3);
        addBowl(5, "Axar Patel",            "3",   17, 1);
        addBowl(5, "T Natarajan",           "4",   29, 3);
        addBowl(5, "Kuldeep Yadav",         "4",   31, 2);
        addBowl(5, "Vipraj Nigam",          "1",    8, 0);

        // ===== Match 6: KKR vs SRH =====
        // KKR bowlers (vs SRH)
        addBowl(6, "Vaibhav Arora",         "4",   47, 2);
        addBowl(6, "Blessing Muzarabani",   "4",   41, 4);
        addBowl(6, "Sunil Narine",          "4",   39, 0);
        addBowl(6, "Varun Chakaravarthy",   "2",   31, 0);
        addBowl(6, "Kartik Tyagi",          "4",   48, 1);
        addBowl(6, "Anukul Roy",            "2",   16, 1);
        // SRH bowlers (vs KKR)
        addBowl(6, "David Payne",           "2",   35, 0);
        addBowl(6, "Harsh Dubey",           "2",   17, 1);
        addBowl(6, "Abhishek Sharma",       "1",   15, 0);
        addBowl(6, "Jaydev Unadkat",        "3",   21, 3);
        addBowl(6, "Eshan Malinga",         "2",   14, 2);
        addBowl(6, "Shivang Kumar",         "4",   41, 0);
        addBowl(6, "Nitish Kumar Reddy",    "2",   17, 2);

        // ===== Match 7: CSK vs PBKS =====
        // PBKS bowlers (vs CSK)
        addBowl(7, "Arshdeep Singh",        "4",   41, 0);
        addBowl(7, "Xavier Bartlett",       "4",   48, 1);
        addBowl(7, "Marco Jansen",          "4",   43, 1);
        addBowl(7, "Vijaykumar Vyshak",     "4",   38, 2);
        addBowl(7, "Yuzvendra Chahal",      "3",   21, 1);
        addBowl(7, "Marcus Stoinis",        "1",   17, 0);
        // CSK bowlers (vs PBKS)
        addBowl(7, "Khaleel Ahmed",         "3",   28, 0);
        addBowl(7, "Matt Henry",            "4",   54, 2);
        addBowl(7, "Anshul Kamboj",         "3.4", 43, 2);
        addBowl(7, "Noor Ahmad",            "4",   38, 0);
        addBowl(7, "Rahul Chahar",          "4",   46, 0);

        // ===== Match 8: DC vs MI =====
        // DC bowlers (vs MI)
        addBowl(8, "Mukesh Kumar",          "3",   26, 2);
        addBowl(8, "Lungi Ngidi",           "4",   34, 1);
        addBowl(8, "Axar Patel",            "4",   22, 1);
        addBowl(8, "Vipraj Nigam",          "3",   24, 1);
        addBowl(8, "Kuldeep Yadav",         "3",   31, 0);
        addBowl(8, "T Natarajan",           "3",   24, 1);
        // MI bowlers (vs DC)
        addBowl(8, "Deepak Chahar",         "3",   20, 1);
        addBowl(8, "Jasprit Bumrah",        "4",   21, 0);
        addBowl(8, "Mitchell Santner",      "3",   22, 1);
        addBowl(8, "Shardul Thakur",        "3",   41, 0);
        addBowl(8, "Mayank Markande",       "2",   20, 0);
        addBowl(8, "Corbin Bosch",          "3.1", 39, 1);

        // ===== Match 9: GT vs RR =====
        // GT bowlers (vs RR)
        addBowl(9, "Mohammed Siraj",        "4",   48, 1);
        addBowl(9, "Kagiso Rabada",         "4",   42, 2);
        addBowl(9, "Ashok Sharma",          "4",   37, 1);
        addBowl(9, "Prasidh Krishna",       "4",   43, 1);
        addBowl(9, "Rashid Khan",           "4",   39, 1);
        // RR bowlers (vs GT)
        addBowl(9, "Jofra Archer",          "4",   38, 0);
        addBowl(9, "Nandre Burger",         "3",   29, 1);
        addBowl(9, "Sandeep Sharma",        "3",   34, 0);
        addBowl(9, "Tushar Deshpande",      "3",   24, 1);
        addBowl(9, "Ravindra Jadeja",       "2",   25, 0);
        addBowl(9, "Riyan Parag",           "1",   11, 1);
        addBowl(9, "Ravi Bishnoi",          "4",   41, 4);

        // ===== Match 10: SRH vs LSG =====
        // LSG bowlers (vs SRH)
        addBowl(10, "Mohammed Shami",       "4",    9, 2);
        addBowl(10, "Digvesh Singh Rathi",  "4",   46, 1);
        addBowl(10, "Prince Yadav",         "4",   34, 2);
        addBowl(10, "Manimaran Siddharth",  "4",   29, 1);
        addBowl(10, "Avesh Khan",           "4",   36, 2);
        // SRH bowlers (vs LSG)
        addBowl(10, "Harsh Dubey",          "4",   18, 2);
        addBowl(10, "Nitish Kumar Reddy",   "1",   14, 0);
        addBowl(10, "Jaydev Unadkat",       "3.5", 50, 0);
        addBowl(10, "Eshan Malinga",        "4",   30, 1);
        addBowl(10, "Harshal Patel",        "3",   16, 0);
        addBowl(10, "Shivang Kumar",        "4",   30, 1);

        // ===== Match 11: RCB vs CSK =====
        // CSK bowlers (vs RCB)
        addBowl(11, "Khaleel Ahmed",        "4",   37, 0);
        addBowl(11, "Matt Henry",           "3",   36, 0);
        addBowl(11, "Anshul Kamboj",        "4",   52, 1);
        addBowl(11, "Noor Ahmad",           "4",   49, 0);
        addBowl(11, "Jamie Overton",        "3",   42, 1);
        addBowl(11, "Shivam Dube",          "2",   30, 1);
        // RCB bowlers (vs CSK)
        addBowl(11, "Jacob Duffy",          "4",   58, 2);
        addBowl(11, "Bhuvneshwar Kumar",    "4",   41, 3);
        addBowl(11, "Abhinandan Singh",     "3.4", 30, 2);
        addBowl(11, "Krunal Pandya",        "3",   36, 2);
        addBowl(11, "Suyash Sharma",        "4",   21, 1);
        addBowl(11, "Romario Shepherd",     "1",   19, 0);
    }

    // ================= HELPER METHODS (no need to edit) =================

    private void addTeam(String name, String shortName) {
        teams.put(shortName, teamRepository.save(new Team(name, shortName)));
    }

    private void addPlayer(String name, String role, String teamShort) {
        players.put(name, playerRepository.save(new Player(name, role, team(teamShort))));
    }

    private void addMatch(int number, String date, String venue,
                          String team1, String team2, String winner, String manOfMatch,
                          int t1Runs, int t1Wickets, String t1Overs,
                          int t2Runs, int t2Wickets, String t2Overs,
                          String resultText) {
        IplMatch m = new IplMatch();
        m.setMatchNumber(number);
        m.setMatchDate(LocalDate.parse(date));
        m.setVenue(venue);
        m.setTeam1(team(team1));
        m.setTeam2(team(team2));
        m.setWinner(winner == null ? null : team(winner));
        m.setManOfTheMatch(manOfMatch == null ? null : player(manOfMatch));
        m.setTeam1Runs(t1Runs);
        m.setTeam1Wickets(t1Wickets);
        m.setTeam1Balls(toBalls(t1Overs));
        m.setTeam2Runs(t2Runs);
        m.setTeam2Wickets(t2Wickets);
        m.setTeam2Balls(toBalls(t2Overs));
        m.setResultText(resultText);
        matches.put(number, matchRepository.save(m));
    }

    private void addBat(int matchNo, String playerName, int runs, int balls,
                        int fours, int sixes, boolean notOut) {
        BattingPerformance b = new BattingPerformance();
        b.setMatch(match(matchNo));
        b.setPlayer(player(playerName));
        b.setRuns(runs);
        b.setBallsFaced(balls);
        b.setFours(fours);
        b.setSixes(sixes);
        b.setNotOut(notOut);
        battingRepository.save(b);
    }

    private void addBowl(int matchNo, String playerName, String overs,
                         int runsConceded, int wickets) {
        BowlingPerformance b = new BowlingPerformance();
        b.setMatch(match(matchNo));
        b.setPlayer(player(playerName));
        b.setBallsBowled(toBalls(overs));
        b.setRunsConceded(runsConceded);
        b.setWickets(wickets);
        bowlingRepository.save(b);
    }

    // "19.4" -> 118 balls, "20" -> 120 balls
    private int toBalls(String overs) {
        String[] parts = overs.split("\\.");
        int fullOvers = Integer.parseInt(parts[0]);
        int extraBalls = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
        return fullOvers * 6 + extraBalls;
    }

    private Team team(String shortName) {
        Team t = teams.get(shortName);
        if (t == null) throw new IllegalArgumentException("Unknown team: " + shortName);
        return t;
    }

    private Player player(String name) {
        Player p = players.get(name);
        if (p == null) throw new IllegalArgumentException("Unknown player: " + name);
        return p;
    }

    private IplMatch match(int number) {
        IplMatch m = matches.get(number);
        if (m == null) throw new IllegalArgumentException("Unknown match number: " + number);
        return m;
    }
}