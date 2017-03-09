package odbg.org.libs;

import java.sql.Timestamp;

/**
 * 
 * @author orlando
 * 
 * @version 1.2
 */
public class JSonG {
    private StringBuilder body = new StringBuilder();
    private boolean arrayType = false;
    private boolean jsonType = false;
    
    // Create Json array type
    public JSonG add( Object value ) {
    	StringBuilder str = new StringBuilder();
    	if( jsonType == false ) {
    		arrayType = true;
    		
    		if( value instanceof String || value instanceof Timestamp)
                str.append("\"").append(value).append("\"");
            else if( value instanceof JSonG )
                str.append(((JSonG) value).toString());

    		value = str.toString();
    		
            if( this.body.equals("") ) {
                this.body.append(value);
            }
            else {
                this.body.append(",").append(value);
            }

    	}
    	else
    		System.out.println("No puede volver a cambiar el tipo de json");

    	return this;
    }

    // Create Json array type
    public JSonG add( Object... value ) {
    	
    	if( jsonType == false ) {
    		arrayType = true;
    	
	        int length = value.length;
	        
	        if( this.body.equals("") )
	        	this.body.append("[");
	        else
	        	this.body.append(",[");
	
	        for( int i = 0; i < length; i++ ) {
	            Object val = value[i];
	
	            if( val instanceof String )
	                val = "\"" + val +"\"";
	            else if( val instanceof JSonG )
	                val = ((JSonG) val).toString();
	
	            this.body.append(val);
	            if( i+1 != length )
	            	this.body.append(",");
	        }
	
	        this.body.append("]");
	
    	}
    	else
    		System.out.println("No puede volver a cambiar el tipo de json");
	    
    	return this;
    }
    
 // Create Json array type
    public JSonG add( Object table[][] ) {
    	
    	if( jsonType == false ) {
    		arrayType = true;
    		
	        int numRows = table.length;
	        int numCols = table[0].length;
	        Object[] labels = table[0];
	
	        if( this.body.equals("") )
	        	this.body.append("[");
	        else
	        	this.body.append(",[");
	
	        for( int i = 1; i < numRows; i++ ) {
	            JSonG tmp = new JSonG();
	
	            for( int j = 0; j < numCols; j++ ) {
	                tmp.add(labels[j], table[i][j]);
	            }
	
	            this.body.append(tmp.toString());
	
	            if( i < numRows - 1)
	            	this.body.append(",");
	        }
	
	        this.body.append("]");

    	}
    	else
    		System.out.println("No puede volver a cambiar el tipo de json");
    	
        return this;
    }

    // Create Json type
    public JSonG add( String name, Object value ) {
    	
    	if( arrayType == false ) {
    		jsonType = true;
    	
	        if( value instanceof String || value instanceof Timestamp)
	            value = "\"" + value + "\"";
	        else if( value instanceof JSonG )
	            value = ((JSonG) value).toString();
	
	        if( this.body.equals("") ) {
	        	this.body.append("\"").append(name).append("\":").append(value);
	        }
	        else {
	        	this.body.append(",\"").append(name).append("\":").append(value);
	        }
    	}
    	else
    		System.out.println("No puede volver a cambiar el tipo de json");
    	
        return this;
    }

    // Create Json type
    public JSonG add( String name, Object... value ) {
    	
    	if( arrayType == false ) {
    		jsonType = true;
    	
	        int length = value.length;
	        if( this.body.equals("") )
	        	this.body.append("\"").append(name).append("\":[");
	        else
	        	this.body.append(",\"").append(name).append("\":[");
	
	        for( int i = 0; i < length; i++ ) {
	            Object val = value[i];
	
	            if( val instanceof String )
	                val = "\"" + val +"\"";
	            else if( val instanceof JSonG )
	                val = ((JSonG) val).toString();
	
	            this.body.append(val);
	            if( i+1 != length )
	            	this.body.append(",");
	        }
	
	        this.body.append("]");
   
    	}
    	else
    		System.out.println("No puede volver a cambiar el tipo de json");
    	
        return this;
    }
    
    // Create Json type
    public JSonG add( String name, Object table[][] ) {
    	
    	if( arrayType == false ) {
    		jsonType = true;
    		
	        int numRows = table.length;
	        int numCols = table[0].length;
	        Object[] labels = table[0];
	
	        if( this.body.equals("") )
	        	this.body.append("\"").append(name).append("\":[");
	        else
	        	this.body.append(",\"").append(name).append("\":[");
	
	        for( int i = 1; i < numRows; i++ ) {
	            JSonG tmp = new JSonG();
	
	            for( int j = 0; j < numCols; j++ ) {
	                tmp.add(labels[j], table[i][j]);
	            }
	
	            this.body.append(tmp.toString());
	
	            if( i < numRows - 1)
	            	this.body.append(",");
	        }
	
	        this.body.append("]");

    	}
    	else
    		System.out.println("No puede volver a cambiar el tipo de json");
    	
        return this;
    }

    @Override
    public String toString() {
    	StringBuilder str = new StringBuilder();
    	if( arrayType )
    		str.append("[").append(this.body.toString()).append("]");
    	else if( jsonType )
    		str.append("{").append(this.body.toString()).append("}");
    	
    	return str.toString();
    }

    public String getPrettyJson() {
        String jsonString = this.toString();
        int tabCount = 0;
        StringBuilder prettyPrintJson = new StringBuilder();
        String lineSeparator = "\r\n";
        String tab = "  ";
        boolean ignoreNext = false;
        boolean inQuote = false;

        char character;

	    /* Loop through each character to style the output */
        for (int i = 0; i < jsonString.length(); i++) {

            character = jsonString.charAt(i);

            if (inQuote) {

                if (ignoreNext) {
                    ignoreNext = false;
                } else if (character == '"') {
                    inQuote = !inQuote;
                }
                prettyPrintJson.append(character);
            } else {

                if (ignoreNext ? ignoreNext = !ignoreNext : ignoreNext);

                switch (character) {

                    case '[':
                        ++tabCount;
                        prettyPrintJson.append(character);
                        prettyPrintJson.append(lineSeparator);
                        printIndent(tabCount, prettyPrintJson, tab);
                        break;

                    case ']':
                        --tabCount;
                        prettyPrintJson.append(lineSeparator);
                        printIndent(tabCount, prettyPrintJson, tab);
                        prettyPrintJson.append(character);
                        break;

                    case '{':
                        ++tabCount;
                        prettyPrintJson.append(character);
                        prettyPrintJson.append(lineSeparator);
                        printIndent(tabCount, prettyPrintJson, tab);
                        break;

                    case '}':
                        --tabCount;
                        prettyPrintJson.append(lineSeparator);
                        printIndent(tabCount, prettyPrintJson, tab);
                        prettyPrintJson.append(character);
                        break;

                    case '"':
                        inQuote = !inQuote;
                        prettyPrintJson.append(character);
                        break;

                    case ',':
                        prettyPrintJson.append(character);
                        prettyPrintJson.append(lineSeparator);
                        printIndent(tabCount, prettyPrintJson, tab);
                        break;

                    case ':':
                        prettyPrintJson.append(character).append(" ");
                        break;

                    case '\\':
                        prettyPrintJson.append(character);
                        ignoreNext = true;
                        break;

                    default:
                        prettyPrintJson.append(character);
                        break;
                }
            }
        }

        return prettyPrintJson.toString();
    }

    private void printIndent(int count, StringBuilder stringBuilder, String indent) {
        for (int i = 0; i < count; i++) {
        	stringBuilder.append(indent);
        }
    }

}
