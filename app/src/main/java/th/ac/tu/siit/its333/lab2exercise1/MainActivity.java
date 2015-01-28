package th.ac.tu.siit.its333.lab2exercise1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    // expr = the current string to be calculated
    StringBuffer expr;
    int result = 0;
    int memory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expr = new StringBuffer();
        updateExprDisplay();
    }

    public void updateExprDisplay() {
        TextView tvExpr = (TextView)findViewById(R.id.tvExpr);
        tvExpr.setText(expr.toString());
    }

    public void recalculate() {
        //Calculate the expression and display the output

        //Split expr into numbers and operators
        //e.g. 123+45/3 --> ["123", "+", "45", "/", "3"]
        //reference: http://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters

        String e = expr.toString();
        String[] tokens = e.split("((?<=\\+)|(?=\\+))|((?<=\\-)|(?=\\-))|((?<=\\*)|(?=\\*))|((?<=/)|(?=/))");

        for(int i=0; i<tokens.length ;i++) {
            /*int tmp = Integer.parseInt(tokens[i]);
            if(i==0){
                result=tmp;
            }
            else if(!(tokens[i].equals("+") || tokens[i].equals("-") || tokens[i].equals("*") || tokens[i].equals("/"))) {
                if ((tokens[i-1].equals("+"))) result += tmp;
                if ((tokens[i-1].equals("-"))) result -= tmp;
                if ((tokens[i-1].equals("*"))) result *= tmp;
                if ((tokens[i-1].equals("/"))) result /= tmp;*/

            if (i%2==0){
                int tmp = Integer.parseInt(tokens[i]);

                if(i==0){
                    result=tmp;
                }
                else {
                    if ((tokens[i - 1].equals("+"))) result += tmp;
                    else if ((tokens[i - 1].equals("-"))) result -= tmp;
                    else if ((tokens[i - 1].equals("*"))) result *= tmp;
                    else if ((tokens[i - 1].equals("/"))) result /= tmp;
                }
            }
        }

        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        tvAns.setText(Integer.toString(result));

    }

    public void digitClicked(View v) {
        //d = the label of the digit button
        String d = ((TextView)v).getText().toString();
        //append the clicked digit to expr
        expr.append(d);
        //update tvExpr
        updateExprDisplay();
        //calculate the result if possible and update tvAns
        recalculate();
    }

    public void equalClicked(View v) {

        TextView tvExpr = (TextView)findViewById(R.id.tvExpr);
        tvExpr.setText(Integer.toString(result));
        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        tvAns.setText(Integer.toString(0));

        Toast t = Toast.makeText(this.getApplicationContext(),
                "Answer is " + result,
                Toast.LENGTH_SHORT);
        t.show();

        result = 0;

    }

    public void operatorClicked(View v) {
        //IF the last character in expr is not an operator and expr is not "",
        //THEN append the clicked operator and updateExprDisplay,
        //ELSE do nothing

        String d = ((TextView)v).getText().toString();
        if (expr.charAt(expr.length()-1)=='+'||expr.charAt(expr.length()-1)=='-'||expr.charAt(expr.length()-1)=='*'||expr.charAt(expr.length()-1)=='/'){
            expr.deleteCharAt(expr.length()-1);
        }
        expr.append(d);
        updateExprDisplay();

    }

    public void MPClicked(View v) {
        memory+=result;

        Toast t = Toast.makeText(this.getApplicationContext(),
                "Memory is " + memory,
                Toast.LENGTH_SHORT);
        t.show();
    }

    public void MSClicked(View v) {
        memory-=result;

        Toast t = Toast.makeText(this.getApplicationContext(),
                "Memory is " + memory,
                Toast.LENGTH_SHORT);
        t.show();
    }

    public void MRlicked(View v) {
        expr = new StringBuffer(Integer.toString(memory));
        updateExprDisplay();
        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        tvAns.setText(Integer.toString(memory));
        result = memory;

        Toast t = Toast.makeText(this.getApplicationContext(),
                "Memory is " + memory,
                Toast.LENGTH_SHORT);
        t.show();
    }

    public void MCClicked(View v) {
        memory=0;

        Toast t = Toast.makeText(this.getApplicationContext(),
                "Memory is " + memory,
                Toast.LENGTH_SHORT);
        t.show();
    }

    public void ACClicked(View v) {
        //Clear expr and updateExprDisplay
        expr = new StringBuffer();
        updateExprDisplay();

        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        tvAns.setText(Integer.toString(0));
        result=0;
        //Display a toast that the value is cleared
        Toast t = Toast.makeText(this.getApplicationContext(),
                "All cleared", Toast.LENGTH_SHORT);
        t.show();
    }

    public void BSClicked(View v) {
        //Remove the last character from expr, and updateExprDisplay
        if (expr.length() > 0) {
            expr.deleteCharAt(expr.length() - 1);
            updateExprDisplay();
            if (expr.length() != 0) {
                recalculate();
            }
            else if(expr.length() == 0){
                TextView tvAns = (TextView)findViewById(R.id.tvAns);
                tvAns.setText(Integer.toString(0));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
