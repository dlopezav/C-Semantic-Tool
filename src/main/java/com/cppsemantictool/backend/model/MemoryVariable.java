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


    public boolean Sign(){
        boolean sign = true;
        switch (this.representation){
            case INTEGER: {
                switch (this.memorySize) {
                    case BITS_16: {
                        sign = this.getValueShort() > 0;
                        break;
                    }
                    case BITS_32: {
                        sign = this.getValueInt() > 0;
                        break;
                    }
                    case BITS_64: {
                        sign = this.getValueLong() > 0;
                        break;
                    }
                }
                break;
            }
            case FLOATING: {
                switch (this.memorySize){
                    case BITS_32: {
                        sign = this.getValueFloat() > 0;
                        break;
                    }
                    case BITS_64: {
                        sign = this.getValueDouble() > 0;
                        break;
                    }
                }
                break;
            }
        }
        return sign;
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

    public void SubtractOne(){
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

    public void CastToShort(){
        switch (this.representation) {
            case INTEGER: {
                switch (this.memorySize){
                    case BITS_16: {
                        MemoryVariable v = new MemoryVariable(this.getValueShort());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                    case BITS_32: {
                        MemoryVariable v = new MemoryVariable(this.getValueInt().shortValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                    case BITS_64: {
                        MemoryVariable v = new MemoryVariable(this.getValueLong().shortValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                }
                break;
            }
            case FLOATING: {
                switch (this.memorySize){
                    case BITS_32: {
                        MemoryVariable v = new MemoryVariable(this.getValueFloat().shortValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                    case BITS_64: {
                        MemoryVariable v = new MemoryVariable(this.getValueDouble().shortValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                }
                break;
            }
        }
    }

    public void CastToInt(){
        switch (this.representation) {
            case INTEGER: {
                switch (this.memorySize){
                    case BITS_16: {
                        MemoryVariable v = new MemoryVariable(this.getValueShort().intValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                    case BITS_32: {
                        MemoryVariable v = new MemoryVariable(this.getValueInt());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                    case BITS_64: {
                        MemoryVariable v = new MemoryVariable(this.getValueLong().intValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                }
                break;
            }
            case FLOATING: {
                switch (this.memorySize){
                    case BITS_32: {
                        MemoryVariable v = new MemoryVariable(this.getValueFloat().intValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                    case BITS_64: {
                        MemoryVariable v = new MemoryVariable(this.getValueDouble().intValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                }
                break;
            }
        }
    }

    public void CastToLong(){
        switch (this.representation) {
            case INTEGER: {
                switch (this.memorySize){
                    case BITS_16: {
                        MemoryVariable v = new MemoryVariable(this.getValueShort().longValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                    case BITS_32: {
                        MemoryVariable v = new MemoryVariable(this.getValueInt().longValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                    case BITS_64: {
                        MemoryVariable v = new MemoryVariable(this.getValueLong());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                }
                break;
            }
            case FLOATING: {
                switch (this.memorySize){
                    case BITS_32: {
                        MemoryVariable v = new MemoryVariable(this.getValueFloat().longValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                    case BITS_64: {
                        MemoryVariable v = new MemoryVariable(this.getValueDouble().longValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                }
                break;
            }
        }
    }

    public void CastToFloat(){
        switch (this.representation) {
            case INTEGER: {
                switch (this.memorySize){
                    case BITS_16: {
                        MemoryVariable v = new MemoryVariable(this.getValueShort().floatValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                    case BITS_32: {
                        MemoryVariable v = new MemoryVariable(this.getValueInt().floatValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                    case BITS_64: {
                        MemoryVariable v = new MemoryVariable(this.getValueLong().floatValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                }
                break;
            }
            case FLOATING: {
                switch (this.memorySize){
                    case BITS_32: {
                        MemoryVariable v = new MemoryVariable(this.getValueFloat());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                    case BITS_64: {
                        MemoryVariable v = new MemoryVariable(this.getValueDouble().floatValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                }
                break;
            }
        }
    }

    public void CastToDouble(){
        switch (this.representation) {
            case INTEGER: {
                switch (this.memorySize){
                    case BITS_16: {
                        MemoryVariable v = new MemoryVariable(this.getValueShort().doubleValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                    case BITS_32: {
                        MemoryVariable v = new MemoryVariable(this.getValueInt().doubleValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                    case BITS_64: {
                        MemoryVariable v = new MemoryVariable(this.getValueLong().doubleValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                }
                break;
            }
            case FLOATING: {
                switch (this.memorySize){
                    case BITS_32: {
                        MemoryVariable v = new MemoryVariable(this.getValueFloat().doubleValue());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                    case BITS_64: {
                        MemoryVariable v = new MemoryVariable(this.getValueDouble());
                        this.value = v.value;
                        this.representation = v.representation;
                        this.memorySize = v.memorySize;
                        break;
                    }
                }
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

    public static MemoryVariable Substract(MemoryVariable a, MemoryVariable b){
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
                                        c = new MemoryVariable(s - b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(s - b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(s - b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(s - b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(s - b.getValueDouble());
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
                                        c = new MemoryVariable(i - b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(i - b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(i - b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(i - b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(i - b.getValueDouble());
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
                                        c = new MemoryVariable(l - b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(l - b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(l - b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(l - b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(l - b.getValueDouble());
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
                                        c = new MemoryVariable(f - b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(f - b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(f - b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(f - b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(f - b.getValueDouble());
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
                                        c = new MemoryVariable(d - b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(d - b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(d - b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(d - b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(d - b.getValueDouble());
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

    public static MemoryVariable Module(MemoryVariable a, MemoryVariable b){
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
                                        c = new MemoryVariable(s % b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(s % b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(s % b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(s % b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(s % b.getValueDouble());
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
                                        c = new MemoryVariable(i % b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(i % b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(i % b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(i % b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(i % b.getValueDouble());
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
                                        c = new MemoryVariable(l % b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(l % b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(l % b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(l % b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(l % b.getValueDouble());
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
                                        c = new MemoryVariable(f % b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(f % b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(f % b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(f % b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(f % b.getValueDouble());
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
                                        c = new MemoryVariable(d % b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        c = new MemoryVariable(d % b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(d % b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        c = new MemoryVariable(d % b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        c = new MemoryVariable(d % b.getValueDouble());
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

    public static MemoryVariable AdditiveInverse(MemoryVariable a){
        MemoryVariable b = null;
        switch (a.representation) {
            case INTEGER: {
                switch (a.memorySize){
                    case BITS_16: {
                        b = new MemoryVariable(-a.getValueShort());
                        break;
                    }
                    case BITS_32: {
                        b = new MemoryVariable(-a.getValueInt());
                        break;
                    }
                    case BITS_64: {
                        MemoryVariable v = new MemoryVariable(-a.getValueLong());
                        break;
                    }
                }
                break;
            }
            case FLOATING: {
                switch (a.memorySize){
                    case BITS_32: {
                        MemoryVariable v = new MemoryVariable(-a.getValueFloat());
                        break;
                    }
                    case BITS_64: {
                        MemoryVariable v = new MemoryVariable(-a.getValueDouble());
                        break;
                    }
                }
                break;
            }
        }
        return b;
    }

    public static boolean GreaterOverflow(MemoryVariable b, MemoryVariable a){
        boolean greater = false;
        switch (b.getRepresentation()){
            case INTEGER: {
                switch (a.getMemorySize()){
                    case BITS_16: {
                        Short s = a.getValueShort();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        greater = s > (Short.MAX_VALUE - b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        greater = s > (Integer.MAX_VALUE - b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        greater = s > (Long.MAX_VALUE - b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        greater = s > (Float.MAX_VALUE - b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        greater = s > (Double.MAX_VALUE - b.getValueDouble());
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
                                        greater = i > (Integer.MAX_VALUE - b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        greater = i > (Integer.MAX_VALUE - b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        greater = i > (Long.MAX_VALUE - b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        greater = i > (Float.MAX_VALUE - b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        greater = i > (Double.MAX_VALUE - b.getValueDouble());
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
                                        greater = l > (Long.MAX_VALUE - b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        greater = l > (Long.MAX_VALUE - b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        greater = l > (Long.MAX_VALUE - b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        greater = l > (Float.MAX_VALUE - b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        greater = l > (Double.MAX_VALUE - b.getValueDouble());
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
                                        greater = f > (Float.MAX_VALUE - b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        greater = f > (Float.MAX_VALUE - b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        greater = f > (Float.MAX_VALUE - b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        greater = f > (Float.MAX_VALUE - b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        greater = f > (Double.MAX_VALUE - b.getValueDouble());
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
                                        greater = d > (Double.MAX_VALUE - b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        greater = d > (Double.MAX_VALUE - b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        greater = d > (Double.MAX_VALUE - b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        greater = d > (Double.MAX_VALUE - b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        greater = d > (Double.MAX_VALUE - b.getValueDouble());
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
        return greater;
    }

    public static boolean LowerOverflow(MemoryVariable b, MemoryVariable a){
        boolean lower = false;
        switch (a.getRepresentation()){
            case INTEGER: {
                switch (a.getMemorySize()){
                    case BITS_16: {
                        Short s = a.getValueShort();
                        switch (b.getRepresentation()){
                            case INTEGER: {
                                switch (b.getMemorySize()) {
                                    case BITS_16: {
                                        lower = s < (Short.MIN_VALUE - b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        lower = s < (Integer.MIN_VALUE - b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        lower = s < (Long.MIN_VALUE - b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        lower = s < (Float.MIN_VALUE - b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        lower = s < (Double.MIN_VALUE - b.getValueDouble());
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
                                        lower = i < (Integer.MIN_VALUE - b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        lower = i < (Integer.MIN_VALUE - b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        lower = i < (Long.MIN_VALUE - b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        lower = i < (Float.MIN_VALUE - b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        lower = i < (Double.MIN_VALUE - b.getValueDouble());
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
                                        lower = l < (Long.MIN_VALUE - b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        lower = l < (Long.MIN_VALUE - b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        lower = l < (Long.MIN_VALUE - b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        lower = l < (Float.MIN_VALUE - b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        lower = l < (Double.MIN_VALUE - b.getValueDouble());
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
                                        lower = f < (Float.MIN_VALUE - b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        lower = f < (Float.MIN_VALUE - b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        lower = f < (Float.MIN_VALUE - b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        lower = f < (Float.MIN_VALUE - b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        lower = f < (Double.MIN_VALUE - b.getValueDouble());
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
                                        lower = d < (Double.MIN_VALUE - b.getValueShort());
                                        break;
                                    }
                                    case BITS_32: {
                                        lower = d < (Double.MIN_VALUE - b.getValueInt());
                                        break;
                                    }
                                    case BITS_64: {
                                        lower = d < (Double.MIN_VALUE - b.getValueLong());
                                        break;
                                    }
                                }
                                break;
                            }
                            case FLOATING: {
                                switch (b.getMemorySize()){
                                    case BITS_32: {
                                        lower = d < (Double.MIN_VALUE - b.getValueFloat());
                                        break;
                                    }
                                    case BITS_64: {
                                        lower = d < (Double.MIN_VALUE - b.getValueDouble());
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
        return lower;
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
