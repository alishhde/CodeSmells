public final class DQ_EvaluationMethodTypeCode
 extends CodeListAdapter<DQ_EvaluationMethodTypeCode, EvaluationMethodType>
{
 /**
     * Empty constructor for JAXB only.
     */
 public DQ_EvaluationMethodTypeCode() {
    }


 /**
     * Creates a new adapter for the given value.
     */
 private DQ_EvaluationMethodTypeCode(final CodeListUID value) {
 super(value);
    }


 /**
     * {@inheritDoc}
     *
     * @return the wrapper for the code list value.
     */
 @Override
 protected DQ_EvaluationMethodTypeCode wrap(final CodeListUID value) {
 return new DQ_EvaluationMethodTypeCode(value);
    }


 /**
     * {@inheritDoc}
     *
     * @return the code list class.
     */
 @Override
 protected Class<EvaluationMethodType> getCodeListClass() {
 return EvaluationMethodType.class;
    }


 /**
     * Invoked by JAXB on marshalling.
     *
     * @return the value to be marshalled.
     */
 @Override
 @XmlElement(name = "DQ_EvaluationMethodTypeCode", namespace = Namespaces.MDQ)
 public CodeListUID getElement() {
 return identifier;
    }


 /**
     * Invoked by JAXB on unmarshalling.
     *
     * @param  value  the unmarshalled value.
     */
 public void setElement(final CodeListUID value) {
 identifier = value;
    }
}