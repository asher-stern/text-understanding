

/* First created by JCasGen Mon Mar 14 16:48:38 IST 2016 */
package com.as.text_understanding.uima_typesystem.pasta;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon Mar 14 17:58:32 IST 2016
 * XML source: /home/asher/data/code/git/text_understanding/src/main/resources/com/as/text_understanding/uima_typesystem/pasta/PastaTypesDescriptor.xml
 * @generated */
public class Predicate extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Predicate.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Predicate() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Predicate(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Predicate(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Predicate(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: verb

  /** getter for verb - gets 
   * @generated
   * @return value of the feature 
   */
  public Annotation getVerb() {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_verb == null)
      jcasType.jcas.throwFeatMissing("verb", "com.as.text_understanding.uima_typesystem.pasta.Predicate");
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_verb)));}
    
  /** setter for verb - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setVerb(Annotation v) {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_verb == null)
      jcasType.jcas.throwFeatMissing("verb", "com.as.text_understanding.uima_typesystem.pasta.Predicate");
    jcasType.ll_cas.ll_setRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_verb, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    