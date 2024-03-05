package com.blueLanguageClub.customsExceptions;

public class ResourceNotFoundException extends RuntimeException  {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
