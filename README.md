# MiniBank — Java Console Banking Simulation

**Student:** Neev Patel  
**Course:** B.Tech AI & ML — 3rd Semester  
**Language:** Java 21 (compatible with Java 17+)

## Project overview and objectives
MiniBank is an educational console application that simulates core banking operations within a terminal environment. Developed as coursework for Object-Oriented Programming (OOP) in Java, the project aims to demonstrate:
- Practical application of OOP principles (classes, encapsulation, state management).
- Safe financial calculations using `BigDecimal` rather than floating-point types.
- Robust user input validation and graceful error handling.
- In-memory collection management using `LinkedHashMap` and `ArrayList`.
- Session-based transaction tracking and audit logging.

## Features
- **Account Creation:** Open a new bank account with an automatically generated account number (starting at 1001) and optional opening balance.
- **Deposit:** Add funds to an existing account with instant balance updates.
- **Withdrawal:** Withdraw funds with automated balance validation to prevent overdrafts.
- **Fund Transfer:** Transfer money between two distinct accounts with atomicity and overdraft protection.
- **Account Directory:** View a tabulated summary of all active accounts, account holders, and balances.
- **Transaction History:** Inspect an audit log of all successful and failed transactions with timestamps.
- **Input Validation:** Strict parsing for whole numbers, valid monetary amounts (up to 2 decimal places), and non-empty account names.

## Java requirements
- JDK 17 or newer (tested with JDK 26)
- Standard terminal / console (Command Prompt, PowerShell, or Bash)

## Folder structure
```
Java-project-/
├── src/
│   └── MiniBank.java
├── docs/
│   ├── PROJECT_REPORT.md
│   └── 25AIML054_CEUE203_OOP_Project_Report.pdf
├── README.md
└── .gitignore
```

## Compilation and execution
From the project root:

```bash
# Compile the Java source code to an output directory
javac -d out src/MiniBank.java

# Run the compiled MiniBank application
java -cp out MiniBank
```

On Windows, the same commands work in Command Prompt or PowerShell if `javac` and `java` are configured on your `PATH`.

## Important limitations
- **In-Memory Storage Only:** All accounts and transaction records reside in memory and are reset when the program exits. No persistent database or file storage is implemented.
- **Simulation Only:** This is an academic demonstration and not suitable for production banking or handling real financial data.
- **No Authentication/Security:** There are no user logins, PINs, passwords, encryption, or multi-user access controls.
- **Single-Threaded:** Designed for interactive single-user console sessions.

## Suggested manual tests
1. **Create Account:** Create an account for "Alice" with an opening deposit of `1000`.
2. **Deposit:** Deposit `250` into account `1001` and verify balance is `1250.00`.
3. **Withdraw:** Withdraw `200` from account `1001` and verify balance is `1050.00`.
4. **Overdraft Protection:** Attempt to withdraw `2000` from account `1001` and confirm it is rejected.
5. **Transfer:** Create account `1002` for "Bob" with `500`, then transfer `100` from `1001` to `1002`. Verify balances (`950.00` and `600.00`).
6. **Self-Transfer Check:** Attempt to transfer funds from `1001` to `1001` and verify it is rejected.
7. **Input Validation:** Enter letters or invalid options at menu prompts to confirm resilience.
8. **Audit Log:** Open transaction history to verify that timestamps, successful transactions, and failed operations are logged.

## Learning outcomes
- Utilizing `Map` and `List` collections for in-memory records.
- Avoiding precision loss by utilizing `BigDecimal` for currency arithmetic.
- Implementing defensive programming through precondition checks before mutating state.
- Structuring a modular, readable console application in Java.
