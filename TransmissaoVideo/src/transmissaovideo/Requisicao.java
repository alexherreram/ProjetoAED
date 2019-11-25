package transmissaovideo;

public class Requisicao implements java.io.Serializable {
    private String data;
    private HuffmanNode obj; 
    /**
     * @return the operando1
     */
    public String getData() {
        return data;
    }
    
    /**
     * @return the operando1
     */
    public HuffmanNode getObj() {
        return obj;
    }

       
    /**
     * @param data the operando1 to set
     */
    public void setData(String data) {
        this.data = data;
    }
    
        /**
     * @param obj the operando1 to set
     */
    public void setObject(HuffmanNode obj) {
        this.obj = obj;
    }
    
}