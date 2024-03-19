package it.giacomobergami.linalg;

import org.ejml.data.DMatrixSparseCSC;
import org.ejml.sparse.csc.CommonOps_DSCC;

import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

public class LabelledMatrix {


    private final List<String> rows;
    private final List<String> cols;
    private final HashMap<String, Integer> invRows, invCols;
    DMatrixSparseCSC adjacencyMatrix;

    public static class LabelledMatrixBuilder {

        HashMap<String, HashMap<String, Double>> m;

        public LabelledMatrixBuilder() {
            m = new HashMap<>();
        }

        public void put(String row, String col, double val) {
            if (!m.containsKey(row)) {
                m.put(row, new HashMap<>());
            }
            m.get(row).put(col, val);
        }

        public Set<String> rows() {
            return m.keySet();
        }

        public Set<String> cols() {
            return m.values().stream().flatMap(x->x.keySet().stream()).collect(Collectors.toSet());
        }

        public LabelledMatrix generate(List<String> rows, List<String> cols) {
            var curr_rows = rows();
            var curr_rows_size = curr_rows.size();
            curr_rows.retainAll(rows);
            if (curr_rows_size != curr_rows.size()) {
                return null;
            }
            var curr_cols = cols();
            var curr_cols_size = curr_cols.size();
            curr_cols.retainAll(cols);
            if (curr_cols_size != curr_cols.size()) {
                return null;
            }
            var result = new LabelledMatrix(rows, cols);
            m.forEach((i,m2)-> m2.forEach((j, val)-> result.put(i,j,val)));
            return result;
        }

    }

    public LabelledMatrix(List<String> rows, List<String> cols) {
        this.rows = new ArrayList<>(rows);
        this.cols = new ArrayList<>(cols);
        invRows = new HashMap<>();
        invCols = new HashMap<>();
        for (int i = 0, N = rows.size(); i<N; i++) {
            invRows.put(rows.get(i), i);
        }
        for (int i = 0, N = cols.size();i<N; i++) {
            invCols.put(cols.get(i), i);
        }
        adjacencyMatrix = new DMatrixSparseCSC(rows.size(), cols.size());
    }

    public LabelledMatrix(List<String> rows, List<String> cols, DMatrixSparseCSC adjacencyMatrix) {
        invRows = new HashMap<>();
        invCols = new HashMap<>();
        this.rows = rows;
        this.cols = cols;
        this.adjacencyMatrix = adjacencyMatrix;
        for (int i = 0, N = rows.size(); i<N; i++) {
            invRows.put(rows.get(i), i);
        }
        for (int i = 0, N = cols.size();i<N; i++) {
            invCols.put(cols.get(i), i);
        }
    }

    public boolean put(String row, String col, double val) {
        Integer i = invRows.get(row);
        if (i != null) {
            Integer j = invCols.get(col);
            if (j != null) {
                adjacencyMatrix.set(i, j, val);
                return true;
            }
            return false;
        }
        return false;
    }

    public LabelledMatrix mult(LabelledMatrix rhs) {
        if (!Objects.equals(cols, rhs.rows))
            return null;
        else {
            var result =  new DMatrixSparseCSC(rows.size(), rhs.cols.size());
            CommonOps_DSCC.mult(adjacencyMatrix, rhs.adjacencyMatrix, result);
            return new LabelledMatrix(rows, rhs.cols, result);
        }
    }

    public LabelledMatrix transpose() {
        var result =  new DMatrixSparseCSC(cols.size(), rows.size());
        CommonOps_DSCC.transpose(adjacencyMatrix, result, null);
        return new LabelledMatrix(cols, rows, result);
    }

    public void print(PrintStream fileStream) {
//        PrintStream fileStream = new PrintStream(fileName);
        int var10001 = adjacencyMatrix.getNumRows();
        fileStream.println("" + var10001 + " " + adjacencyMatrix.getNumCols() + " real");
        System.out.print("{");
        for(int i = 0; i < adjacencyMatrix.getNumRows(); ++i) {
            System.out.print("{");
            for(int j = 0; j < adjacencyMatrix.getNumCols(); ++j) {
                double var5 = adjacencyMatrix.get(i, j);
                System.out.print("" + var5 + "");
                if (j != (adjacencyMatrix.getNumCols()-1))
                    System.out.print(",");
            }
            System.out.print("}");
            if (i != (adjacencyMatrix.getNumRows()-1))
                System.out.print(",");
            fileStream.println();
        }
//        fileStream.close();
    }

}
