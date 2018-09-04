package com.sim.board.controller;

public interface SimpleTronMethod {

	// Input/output operations:
	public final int READ = 10; // Read a word from the keyboard into a specific location in memory.
	public final int WRITE = 11; // Write a word from a specific location in memory to the screen.

	// Load/store operations:
	public final int LOAD = 20; // Load a word from a specific location in memory into the accumulator.
	public final int STORE = 21; // Store a word from the accumulator into a specific location in memory.

	// Arithmetic operations:
	public final int ADD = 30; // Add a word from a specific location in memory to the word in the accumulator.
	public final int SUBTRACT = 31; // Subtract a word from a specific location in memory from the word in the
										// accumulator.
	public final int DIVIDE = 32; // Divide a word from a specific location in memory into the word in the
									// accumulator.
	public final int MULTIPLY = 33; // Multiply a word from a specific location in memory by the word in the
										// accumulator.

	// Transfer-of-control operations:
	public final int BRANCH = 40; // Branch to a specific location in memory.
	public final int BRANCHNEG = 41; // Branch to a specific location in memory if the accumulator is negative.
	public final int BRANCHZERO = 42; // Branch to a specific location in memory if the accumulator is zero.
	public final int HALT = 43; // Halt. The program has completed its task.

}
