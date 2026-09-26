package com.antony.madr.infra.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(EExceptionsTypes type) {super(type + " not listed in MADR");}

}
