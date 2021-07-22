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

    public MemoryVariable(Short value){
        this(ByteSize.BITS_16, NumberType.INTEGER);
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.putShort(value);
        this.value = bb.array();
    }

    public MemoryVariable(Integer value){
        this(ByteSize.BITS_32, NumberType.INTEGER);
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(value);
        this.value = bb.array();
    }

    public MemoryVariable(Long value){
        this(ByteSize.BITS_64, NumberType.INTEGER);
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(value);
        this.value = bb.array();
    }

    public MemoryVariable(Float value){
        this(ByteSize.BITS_32, NumberType.FLOATING);
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putFloat(value);
        this.value = bb.array();
    }

    public MemoryVariable(Double value){
        this(ByteSize.BITS_64, NumberType.FLOATING);
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putDouble(value);
        this.value = bb.array();
    }

    public byte[] getArray(){
        return this.value;
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

    public Float getValueFloat() {
        ByteBuffer bb = ByteBuffer.wrap(this.value);
        return bb.getFloat();
    }

    public Double getValueDouble() {
        ByteBuffer bb = ByteBuffer.wrap(this.value);
        return bb.getDouble();
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

    public static ByteSize MaxMemorySize(ByteSize a, ByteSize b){
        return a.ordinal() >= b.ordinal() ? a : b;
    }

    public static MemoryVariable Add(MemoryVariable a, MemoryVariable b){
        MemoryVariable c = null;
        switch (a.getRepresentation()){
            case INTEGER: {
                switch (a.getMemorySize()){
                    case BITS_16: {
                        Short s = a.getValueShort();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        c = new MemoryVariable(s + b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(s + b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(s + b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(s + b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(s + b.getValueDouble());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    case BITS_32: {
                        Integer i = a.getValueInt();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        c = new MemoryVariable(i + b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(i + b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(i + b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(i + b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(i + b.getValueDouble());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                    case BITS_64: {
                        Long l = a.getValueLong();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        c = new MemoryVariable(l + b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(l + b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(l + b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(l + b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(l + b.getValueDouble());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
                break;
            }
            case FLOATING: {
                switch (a.getMemorySize()){
                    case BITS_32: {
                        Float f = a.getValueFloat();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        c = new MemoryVariable(f + b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(f + b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(f + b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(f + b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(f + b.getValueDouble());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                    case BITS_64: {
                        Double d = a.getValueDouble();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        c = new MemoryVariable(d + b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(d + b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(d + b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(d + b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(d + b.getValueDouble());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        return c;
    }

    public static MemoryVariable Multiply(MemoryVariable a, MemoryVariable b){
        MemoryVariable c = null;
        switch (a.getRepresentation()){
            case INTEGER: {
                switch (a.getMemorySize()){
                    case BITS_16: {
                        Short s = a.getValueShort();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        c = new MemoryVariable(s * b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(s * b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(s * b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(s * b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(s * b.getValueDouble());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    case BITS_32: {
                        Integer i = a.getValueInt();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        c = new MemoryVariable(i * b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(i * b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(i * b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(i * b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(i * b.getValueDouble());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                    case BITS_64: {
                        Long l = a.getValueLong();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        c = new MemoryVariable(l * b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(l * b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(l * b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(l * b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(l * b.getValueDouble());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
                break;
            }
            case FLOATING: {
                switch (a.getMemorySize()){
                    case BITS_32: {
                        Float f = a.getValueFloat();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        c = new MemoryVariable(f * b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(f * b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(f * b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(f * b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(f * b.getValueDouble());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                    case BITS_64: {
                        Double d = a.getValueDouble();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        c = new MemoryVariable(d * b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(d * b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(d * b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(d * b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(d * b.getValueDouble());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        return c;
    }

    public static MemoryVariable Divide(MemoryVariable a, MemoryVariable b){
        MemoryVariable c = null;
        switch (a.getRepresentation()){
            case INTEGER: {
                switch (a.getMemorySize()){
                    case BITS_16: {
                        Short s = a.getValueShort();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        c = new MemoryVariable(s / b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(s / b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(s / b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(s / b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(s / b.getValueDouble());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    case BITS_32: {
                        Integer i = a.getValueInt();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        c = new MemoryVariable(i / b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(i / b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(i / b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(i / b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(i / b.getValueDouble());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                    case BITS_64: {
                        Long l = a.getValueLong();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        c = new MemoryVariable(l / b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(l / b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(l / b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(l / b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(l / b.getValueDouble());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
                break;
            }
            case FLOATING: {
                switch (a.getMemorySize()){
                    case BITS_32: {
                        Float f = a.getValueFloat();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        c = new MemoryVariable(f / b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(f / b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(f / b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(f / b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(f / b.getValueDouble());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                    case BITS_64: {
                        Double d = a.getValueDouble();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        c = new MemoryVariable(d * b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(d * b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(d * b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(d * b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(d * b.getValueDouble());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        return c;
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
