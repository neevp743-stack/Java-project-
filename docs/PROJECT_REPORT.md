# MiniBank Project Report

## 1. Title
MiniBank — Java Console Banking Simulation

## 2. Problem statement
A beginner-friendly banking simulation is needed to demonstrate how common account operations can be represented in a Java program while validating input and preventing invalid withdrawals or transfers.

## 3. Objectives
- Create and manage simple bank account records.
- Perform deposits, withdrawals, and transfers.
- Reject invalid amounts and transactions that exceed available balance.
- Display account balances and a transaction history.
- Apply core Java programming concepts in a small, understandable application.

## 4. Tools and technologies
- Java (JDK 17 or newer)
- Java standard library: `Scanner`, `Map`, `List`, `BigDecimal`, `LocalDateTime`
- Any Java-compatible editor or terminal

## 5. Main workflow
1. Start the application and display the menu.
2. Read the user's choice.
3. Validate the input.
4. Perform the selected operation only if account and amount checks pass.
5. Record the operation in the session history.
6. Return to the menu until Exit is selected.

## 6. Main data model
Each account stores an account number, account holder name, and balance. A map associates account numbers with accounts. A list stores time-stamped transaction descriptions.

## 7. Validation and safeguards
- Account holder names cannot be empty or longer than 60 characters.
- Amounts must be non-negative, and deposits/withdrawals/transfers must be greater than zero.
- Currency amounts accept at most two decimal places.
- Withdrawals and transfers are rejected if the source balance is insufficient.
- Transfers to the same account are rejected.
- Invalid menu input is handled without terminating the program.

## 8. Testing plan
Use the manual test checklist in the README. Record actual outcomes after running the program; do not claim tests passed unless they have been performed.

## 9. Limitations and future scope
Current account data is held in memory and disappears when the program exits. Future improvements could include persistent storage, account authentication, automated unit tests, and a graphical interface. Any security features would need proper design before real use.

## 10. Conclusion
MiniBank provides a small demonstration of Java-based account management and input validation. It is intended only for education and is not a real banking system.
