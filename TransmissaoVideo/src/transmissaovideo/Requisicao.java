package transmissaovideo;

import java.util.List;

public class Requisicao implements java.io.Serializable {
    private Object data;
    private HuffmanNode obj; 
    private String algoritmo;
    /**
     * @return the operando1
     */
    public Object getData() {
        return data;
    }
    
    /**
     * @return the operando1
     */
    public HuffmanNode getObj() {
        return obj;
    }
    
    /**
     * @return the operando1
     */
    public String getAlgoritmo() {
        return algoritmo;
    }
       
    /**
     * @param data the operando1 to set
     */
    public void setData(Object data) {
        this.data = data;
    }
    
        /**
     * @param obj the operando1 to set
     */
    public void setObject(HuffmanNode obj) {
        this.obj = obj;
    }
    
           /**
     * @param obj the operando1 to set
     */
    public void setAlgoritmo(String obj) {
        this.algoritmo = obj;
    } 
    
}