

/* First created by JCasGen Mon Mar 14 16:48:38 IST 2016 */
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
public class Argument extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Argument.class);
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
  protected Argument() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Argument(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Argument(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Argument(JCas jcas, int begin, int end) {
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
  //* Feature: argumentType

  /** getter for argumentType - gets 
   * @generated
   * @return value of the feature 
   */
  public ArgumentType getArgumentType() {
    if (Argument_Type.featOkTst && ((Argument_Type)jcasType).casFeat_argumentType == null)
      jcasType.jcas.throwFeatMissing("argumentType", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    return (ArgumentType)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Argument_Type)jcasType).casFeatCode_argumentType)));}
    
  /** setter for argumentType - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setArgumentType(ArgumentType v) {
    if (Argument_Type.featOkTst && ((Argument_Type)jcasType).casFeat_argumentType == null)
      jcasType.jcas.throwFeatMissing("argumentType", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    jcasType.ll_cas.ll_setRefValue(addr, ((Argument_Type)jcasType).casFeatCode_argumentType, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: items

  /** getter for items - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getItems() {
    if (Argument_Type.featOkTst && ((Argument_Type)jcasType).casFeat_items == null)
      jcasType.jcas.throwFeatMissing("items", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Argument_Type)jcasType).casFeatCode_items)));}
    
  /** setter for items - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setItems(FSArray v) {
    if (Argument_Type.featOkTst && ((Argument_Type)jcasType).casFeat_items == null)
      jcasType.jcas.throwFeatMissing("items", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    jcasType.ll_cas.ll_setRefValue(addr, ((Argument_Type)jcasType).casFeatCode_items, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for items - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public ArgumentItem getItems(int i) {
    if (Argument_Type.featOkTst && ((Argument_Type)jcasType).casFeat_items == null)
      jcasType.jcas.throwFeatMissing("items", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Argument_Type)jcasType).casFeatCode_items), i);
    return (ArgumentItem)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Argument_Type)jcasType).casFeatCode_items), i)));}

  /** indexed setter for items - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setItems(int i, ArgumentItem v) { 
    if (Argument_Type.featOkTst && ((Argument_Type)jcasType).casFeat_items == null)
      jcasType.jcas.throwFeatMissing("items", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Argument_Type)jcasType).casFeatCode_items), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Argument_Type)jcasType).casFeatCode_items), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: clause

  /** getter for clause - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getClause() {
    if (Argument_Type.featOkTst && ((Argument_Type)jcasType).casFeat_clause == null)
      jcasType.jcas.throwFeatMissing("clause", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Argument_Type)jcasType).casFeatCode_clause);}
    
  /** setter for clause - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setClause(boolean v) {
    if (Argument_Type.featOkTst && ((Argument_Type)jcasType).casFeat_clause == null)
      jcasType.jcas.throwFeatMissing("clause", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Argument_Type)jcasType).casFeatCode_clause, v);}    
   
    
  //*--------------*
  //* Feature: prepositions

  /** getter for prepositions - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getPrepositions() {
    if (Argument_Type.featOkTst && ((Argument_Type)jcasType).casFeat_prepositions == null)
      jcasType.jcas.throwFeatMissing("prepositions", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Argument_Type)jcasType).casFeatCode_prepositions)));}
    
  /** setter for prepositions - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPrepositions(FSArray v) {
    if (Argument_Type.featOkTst && ((Argument_Type)jcasType).casFeat_prepositions == null)
      jcasType.jcas.throwFeatMissing("prepositions", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    jcasType.ll_cas.ll_setRefValue(addr, ((Argument_Type)jcasType).casFeatCode_prepositions, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for prepositions - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Annotation getPrepositions(int i) {
    if (Argument_Type.featOkTst && ((Argument_Type)jcasType).casFeat_prepositions == null)
      jcasType.jcas.throwFeatMissing("prepositions", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Argument_Type)jcasType).casFeatCode_prepositions), i);
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Argument_Type)jcasType).casFeatCode_prepositions), i)));}

  /** indexed setter for prepositions - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setPrepositions(int i, Annotation v) { 
    if (Argument_Type.featOkTst && ((Argument_Type)jcasType).casFeat_prepositions == null)
      jcasType.jcas.throwFeatMissing("prepositions", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Argument_Type)jcasType).casFeatCode_prepositions), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Argument_Type)jcasType).casFeatCode_prepositions), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    