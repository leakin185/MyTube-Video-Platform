package com.leajava.exceptions;

// Checked -> extend Exception class
// Unchecked (runtime) -> extend RuntimeException class

public class InsufficientFundsException extends Exception {
  public InsufficientFundsException() {
    super("Insufficient funds in your account.");
  }

  public InsufficientFundsException(String message) {
    super(message);
  }
}
