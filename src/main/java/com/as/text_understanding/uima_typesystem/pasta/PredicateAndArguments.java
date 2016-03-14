

/* First created by JCasGen Mon Mar 14 17:58:27 IST 2016 */
package com.as.text_understanding.uima_typesystem.pasta;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon Mar 14 17:58:32 IST 2016
 * XML source: /home/asher/data/code/git/text_understanding/src/main/resources/com/as/text_understanding/uima_typesystem/pasta/PastaTypesDescriptor.xml
 * @generated */
public class PredicateAndArguments extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(PredicateAndArguments.class);
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
  protected PredicateAndArguments() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public PredicateAndArguments(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public PredicateAndArguments(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public PredicateAndArguments(JCas jcas, int begin, int end) {
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
  //* Feature: predicate

  /** getter for predicate - gets 
   * @generated
   * @return value of the feature 
   */
  public Predicate getPredicate() {
    if (PredicateAndArguments_Type.featOkTst && ((PredicateAndArguments_Type)jcasType).casFeat_predicate == null)
      jcasType.jcas.throwFeatMissing("predicate", "com.as.text_understanding.uima_typesystem.pasta.PredicateAndArguments");
    return (Predicate)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((PredicateAndArguments_Type)jcasType).casFeatCode_predicate)));}
    
  /** setter for predicate - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPredicate(Predicate v) {
    if (PredicateAndArguments_Type.featOkTst && ((PredicateAndArguments_Type)jcasType).casFeat_predicate == null)
      jcasType.jcas.throwFeatMissing("predicate", "com.as.text_understanding.uima_typesystem.pasta.PredicateAndArguments");
    jcasType.ll_cas.ll_setRefValue(addr, ((PredicateAndArguments_Type)jcasType).casFeatCode_predicate, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: arguments

  /** getter for arguments - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getArguments() {
    if (PredicateAndArguments_Type.featOkTst && ((PredicateAndArguments_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "com.as.text_understanding.uima_typesystem.pasta.PredicateAndArguments");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((PredicateAndArguments_Type)jcasType).casFeatCode_arguments)));}
    
  /** setter for arguments - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setArguments(FSArray v) {
    if (PredicateAndArguments_Type.featOkTst && ((PredicateAndArguments_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "com.as.text_understanding.uima_typesystem.pasta.PredicateAndArguments");
    jcasType.ll_cas.ll_setRefValue(addr, ((PredicateAndArguments_Type)jcasType).casFeatCode_arguments, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for arguments - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Argument getArguments(int i) {
    if (PredicateAndArguments_Type.featOkTst && ((PredicateAndArguments_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "com.as.text_understanding.uima_typesystem.pasta.PredicateAndArguments");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((PredicateAndArguments_Type)jcasType).casFeatCode_arguments), i);
    return (Argument)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((PredicateAndArguments_Type)jcasType).casFeatCode_arguments), i)));}

  /** indexed setter for arguments - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setArguments(int i, Argument v) { 
    if (PredicateAndArguments_Type.featOkTst && ((PredicateAndArguments_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "com.as.text_understanding.uima_typesystem.pasta.PredicateAndArguments");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((PredicateAndArguments_Type)jcasType).casFeatCode_arguments), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((PredicateAndArguments_Type)jcasType).casFeatCode_arguments), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    