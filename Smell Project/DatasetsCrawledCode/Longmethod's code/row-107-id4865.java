 private void appendXmlComparison1(SQLBuffer buf, String op,
 FilterValue lhs, FilterValue rhs) {
 boolean castrhs = false;
 Class<?> rc = Filters.wrap(rhs.getType());
 int type = 0;
 if (rhs.isConstant()) {
 type = getJDBCType(JavaTypes.getTypeCode(rc), false);
 castrhs = true;
        }


 appendXmlExists(buf, lhs);


 buf.append(" ").append(op).append(" ");


 buf.append("$");
 if (castrhs)
 buf.append("Parm");
 else
 rhs.appendTo(buf);


 buf.append("]' PASSING ");
 appendXmlVar(buf, lhs);
 buf.append(", ");


 if (castrhs)
 appendCast(buf, rhs, type);
 else
 rhs.appendTo(buf);


 buf.append(" AS \"");
 if (castrhs)
 buf.append("Parm");
 else
 rhs.appendTo(buf);
 buf.append("\")");
    }