
/* First created by JCasGen Mon Mar 14 16:48:38 IST 2016 */
package com.as.text_understanding.uima_typesystem.pasta;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Mon Mar 14 17:58:32 IST 2016
 * @generated */
public class Argument_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Argument_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Argument_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Argument(addr, Argument_Type.this);
  			   Argument_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Argument(addr, Argument_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Argument.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.as.text_understanding.uima_typesystem.pasta.Argument");
 
  /** @generated */
  final Feature casFeat_argumentType;
  /** @generated */
  final int     casFeatCode_argumentType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getArgumentType(int addr) {
        if (featOkTst && casFeat_argumentType == null)
      jcas.throwFeatMissing("argumentType", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    return ll_cas.ll_getRefValue(addr, casFeatCode_argumentType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setArgumentType(int addr, int v) {
        if (featOkTst && casFeat_argumentType == null)
      jcas.throwFeatMissing("argumentType", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    ll_cas.ll_setRefValue(addr, casFeatCode_argumentType, v);}
    
  
 
  /** @generated */
  final Feature casFeat_items;
  /** @generated */
  final int     casFeatCode_items;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getItems(int addr) {
        if (featOkTst && casFeat_items == null)
      jcas.throwFeatMissing("items", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    return ll_cas.ll_getRefValue(addr, casFeatCode_items);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setItems(int addr, int v) {
        if (featOkTst && casFeat_items == null)
      jcas.throwFeatMissing("items", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    ll_cas.ll_setRefValue(addr, casFeatCode_items, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getItems(int addr, int i) {
        if (featOkTst && casFeat_items == null)
      jcas.throwFeatMissing("items", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_items), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_items), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_items), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setItems(int addr, int i, int v) {
        if (featOkTst && casFeat_items == null)
      jcas.throwFeatMissing("items", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_items), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_items), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_items), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_clause;
  /** @generated */
  final int     casFeatCode_clause;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getClause(int addr) {
        if (featOkTst && casFeat_clause == null)
      jcas.throwFeatMissing("clause", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_clause);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setClause(int addr, boolean v) {
        if (featOkTst && casFeat_clause == null)
      jcas.throwFeatMissing("clause", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_clause, v);}
    
  
 
  /** @generated */
  final Feature casFeat_prepositions;
  /** @generated */
  final int     casFeatCode_prepositions;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getPrepositions(int addr) {
        if (featOkTst && casFeat_prepositions == null)
      jcas.throwFeatMissing("prepositions", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    return ll_cas.ll_getRefValue(addr, casFeatCode_prepositions);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPrepositions(int addr, int v) {
        if (featOkTst && casFeat_prepositions == null)
      jcas.throwFeatMissing("prepositions", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    ll_cas.ll_setRefValue(addr, casFeatCode_prepositions, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getPrepositions(int addr, int i) {
        if (featOkTst && casFeat_prepositions == null)
      jcas.throwFeatMissing("prepositions", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_prepositions), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_prepositions), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_prepositions), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setPrepositions(int addr, int i, int v) {
        if (featOkTst && casFeat_prepositions == null)
      jcas.throwFeatMissing("prepositions", "com.as.text_understanding.uima_typesystem.pasta.Argument");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_prepositions), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_prepositions), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_prepositions), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Argument_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_argumentType = jcas.getRequiredFeatureDE(casType, "argumentType", "com.as.text_understanding.uima_typesystem.pasta.ArgumentType", featOkTst);
    casFeatCode_argumentType  = (null == casFeat_argumentType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_argumentType).getCode();

 
    casFeat_items = jcas.getRequiredFeatureDE(casType, "items", "uima.cas.FSArray", featOkTst);
    casFeatCode_items  = (null == casFeat_items) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_items).getCode();

 
    casFeat_clause = jcas.getRequiredFeatureDE(casType, "clause", "uima.cas.Boolean", featOkTst);
    casFeatCode_clause  = (null == casFeat_clause) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_clause).getCode();

 
    casFeat_prepositions = jcas.getRequiredFeatureDE(casType, "prepositions", "uima.cas.FSArray", featOkTst);
    casFeatCode_prepositions  = (null == casFeat_prepositions) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_prepositions).getCode();

  }
}



    