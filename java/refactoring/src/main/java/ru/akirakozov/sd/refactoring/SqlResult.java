package ru.akirakozov.sd.refactoring;

import java.sql.ResultSet;

public abstract class SqlResult {
    protected final StringBuilder builder = new StringBuilder();
    public abstract void setResult(ResultSet result);
    public String data() {
        return builder.toString();
    }
}
