package com.cppsemantictool.backend.model;

import java.nio.ByteBuffer;

public class MemoryVariable {

    private ByteSize memorySize;
    private NumberType representation;
    private byte[] value;

    private MemoryVariable(ByteSize memorySize, NumberType representation) {
        this.memorySize = memorySize;
        this.representation = representation;
    }

    public MemoryVariable(ByteSize memorySize, NumberType representation, Short value){
        this(memorySize, representation);
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.putShort(value);
        this.value = bb.array();
    }

    public MemoryVariable(ByteSize memorySize, NumberType representation, Integer value){
        this(memorySize, representation);
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(value);
        this.value = bb.array();
    }

    public MemoryVariable(ByteSize memorySize, NumberType representation, Long value){
        this(memorySize, representation);
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(value);
        this.value = bb.array();
    }

    public ByteSize getMemorySize() {
        return this.memorySize;
    }

    public void setMemorySize(ByteSize memorySize) {
        this.memorySize = memorySize;
    }

    public NumberType getRepresentation() {
        return this.representation;
    }

    public void setRepresentation(NumberType representation) {
        this.representation = representation;
    }

    public byte[] getValue() {
        return this.value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public enum ByteSize{
        BITS_8,
        BITS_16,
        BITS_32,
        BITS_64
    }

    public enum NumberType{
        INTEGER,
        FLOATING
    }
}
