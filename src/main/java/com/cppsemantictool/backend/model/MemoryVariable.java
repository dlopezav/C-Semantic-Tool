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

    public Short getValueShort() {
        ByteBuffer bb = ByteBuffer.wrap(this.value);
        return bb.getShort();
    }

    public Integer getValueInt() {
        ByteBuffer bb = ByteBuffer.wrap(this.value);
        return bb.getInt();
    }


    public Long getValueLong() {
        ByteBuffer bb = ByteBuffer.wrap(this.value);
        return bb.getLong();
    }

    public void AddOne(){
        switch (this.memorySize){
            case BITS_16: {
                Short value = this.getValueShort();
                ByteBuffer bb = ByteBuffer.allocate(2);
                bb.putShort(++value);
                this.value = bb.array();
                break;
            }
            case BITS_32: {
                Integer value = this.getValueInt();
                ByteBuffer bb = ByteBuffer.allocate(4);
                bb.putInt(++value);
                this.value = bb.array();
                break;
            }
            case BITS_64: {
                Long value = this.getValueLong();
                ByteBuffer bb = ByteBuffer.allocate(8);
                bb.putLong(++value);
                this.value = bb.array();
                break;
            }
        }
    }

    public void RestOne(){
        switch (this.memorySize){
            case BITS_16: {
                Short value = this.getValueShort();
                ByteBuffer bb = ByteBuffer.allocate(2);
                bb.putShort(--value);
                this.value = bb.array();
                break;
            }
            case BITS_32: {
                Integer value = this.getValueInt();
                ByteBuffer bb = ByteBuffer.allocate(4);
                bb.putInt(--value);
                this.value = bb.array();
                break;
            }
            case BITS_64: {
                Long value = this.getValueLong();
                ByteBuffer bb = ByteBuffer.allocate(8);
                bb.putLong(--value);
                this.value = bb.array();
                break;
            }
        }
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
