package com.antony.madr.infra.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(EExceptionsRolesTypes type) {super(type + " not found in MADR");}

}
