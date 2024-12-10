# Traffic Flow Simulation using the Jacobi Method

## Overview
The **Traffic Flow Simulation using the Jacobi Method** is a Java-based application that models traffic flow across a network of junctions. It uses the **Jacobi Method**, a numerical technique, to solve systems of linear equations iteratively. This project provides an educational tool to explore traffic dynamics and analyze flow behavior under varying conditions.

## Features
- **User-Friendly GUI**: A graphical user interface built with Java Swing allows intuitive user interaction.
- **Iterative Simulation**: Simulates traffic flow iteratively, showing the evolution of conditions over multiple iterations.
- **Input Guidance**: Includes a guide tab to assist users in understanding input parameters and configurations.
- **Tabbed Interface**: Organizes outputs, input variables, and guides into structured tabs.
- **Convergence Check**: Verifies if the Jacobi method converges and notifies users about the status.

## Inputs
1. **Number of Junctions (n)**: Specifies the traffic network's complexity.
2. **Connectivity Matrix (A)**: Represents traffic flow relationships between junctions.
3. **External Forces (b)**: Indicates external traffic entering each junction.
4. **Initial Guess (x)**: Represents the initial conditions of traffic flow.

## Outputs
- **Traffic Flow Results**: Displays computed traffic flow at each junction.
- **Iteration Details**: Tabulated data showing changes in traffic flow for each iteration.
- **Convergence Status**: Informs users whether the simulation successfully converged.

## Limitations
1. **Model Simplicity**: Simplified traffic model; does not capture all real-world complexities.
2. **Numeric Stability**: The Jacobi method may fail to converge for certain configurations.
3. **Limited Visualization**: The focus is on textual rather than graphical output.

## Installation
1. Clone this repository:
   ```bash
   git clone https://github.com/Gengo-bit/Traffic-Flow-Simulation-using-the-Jacobi-Method.git
   ```
2. Open the project in your favorite Java IDE (e.g., IntelliJ IDEA, Eclipse).
3. Build and run the TrafficFlowSimulation class.

## Usage
1. Open the project in your favorite Java IDE (e.g., IntelliJ IDEA, Eclipse).
2. Build and run the `TrafficFlowSimulation` class.
3. Enter the number of traffic junctions.
4. Input the connectivity matrix, external forces, and initial guess.
5. Click "Simulate Traffic Flow" to view results.

## Contributing
Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a new branch for your feature/bug fix.
3. Submit a pull request with a detailed explanation of your changes.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.

---

### Developers
- **Paul Emmanuel Corsino** - Leader, Main Programmer, Sub Documentation
- **Klinnsonveins Yee** - Main Documentation, Sub Programmer

---
2024 © Department of Computer Engineering

