package com.example.roguechesscapstone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;

//import org.tensorflow.lite.Interpreter;

//import org.tensorflow.lite.InterpreterApi;

//import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private Button Versus2;
    private Button Single;
    public String PvsC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN); //get a stable fullscreen layout
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        Versus2 = (Button) findViewById(R.id.button2);
        Versus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGame();
//                try {
//                    RNNmodel50KGamesTanhCenter25B200E model = RNNmodel50KGamesTanhCenter25B200E.newInstance(MainActivity.this); //was getApplicationContext()
//
//                    // Creates inputs for reference.
//                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 5, 384}, DataType.FLOAT32);
//                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 5 * 384);
//
//                    byteBuffer.order(ByteOrder.nativeOrder());
//
//                    inputFeature0.loadBuffer(byteBuffer);
//
//                    // Runs model inference and gets result.
//                    RNNmodel50KGamesTanhCenter25B200E.Outputs outputs = model.process(inputFeature0);
//                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
//
//                    // Releases model resources if no longer used.
//                    model.close();
//                } catch (IOException ex) {
//                     TODO Handle the exception
//                }
            }
        });

        Single = (Button) findViewById(R.id.button);
        Single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity2();

            }
        });
    }


    public void openGame() { //Player Vs Player Button

        PvsC = "PvPMatch";
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        intent.putExtra("PvC", PvsC);
        startActivity(intent);
    }

    public void openMainActivity2() { //Single Player Button
        PvsC = "SingleMatch";
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        intent.putExtra("PvC", PvsC);
        startActivity(intent);
    }

//    private MappedByteBuffer loadModelFile() throws IOException {
//        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("RNNmodel50KGamesTanhCenter25B200E");
//        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
//        FileChannel fileChannel = inputStream.getChannel();
//        long startOffset = fileDescriptor.getStartOffset();
//        long declareLength = fileDescriptor.getDeclaredLength();
//        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declareLength);
//    }
//    private void doInference(String inputString, View v) {
//
//        //Initialize matrix
//        int[][][] newmatrix = new int[6][8][8];
//        for (int a = 0; a < 6; a++) {
//            for (int b = 0; b < 8; b++) {
//                for (int c = 0; c < 8; c++) {
//                    newmatrix[a][b][c] = 0;
//                }
//            }
//        }
//        for (int b = 0; b < 8; b++) {
//            for (int c = 0; c < 8; c++) {
//                Piece d = Board[b][c].getPiece(); //get piece p
//                if (Board[b][c].getPiece() != null) {
//                    if (d instanceof King) {
//                        if (d.isWhite()) {
//                            newmatrix[0][b][c] = -1;
//                        } else {
//                            newmatrix[0][b][c] = 1;
//                        }
//                    } else if (d instanceof Queen) {
//                        if (d.isWhite()) {
//                            newmatrix[4][b][c] = -1;
//                        } else {
//                            newmatrix[4][b][c] = 1;
//                        }
//                    } else if (d instanceof Rook) {
//                        if (d.isWhite()) {
//                            newmatrix[3][b][c] = -1;
//                        } else {
//                            newmatrix[3][b][c] = 1;
//                        }
//                    } else if (d instanceof Bishop) {
//                        if (d.isWhite()) {
//                            newmatrix[2][b][c] = -1;
//                        } else {
//                            newmatrix[2][b][c] = 1;
//                        }
//                    } else if (d instanceof Knight) {
//                        if (d.isWhite()) {
//                            newmatrix[1][b][c] = -1;
//                        } else {
//                            newmatrix[1][b][c] = 1;
//                        }
//                    } else if (d instanceof Pawn) {
//                        if (d.isWhite()) {
//                            newmatrix[0][b][c] = -1;
//                        } else {
//                            newmatrix[0][b][c] = 1;
//                        }
//                    }
//
//                }
//
//            }
//        }
//
//        float[][] reshapeInData = new float[5][384];
//        for (int board = 0; board < 5; board++) {
//            int cnt = 0;
//            for (int a = 0; a < 6; a++) {
//                for (int b = 0; b < 8; b++) {
//                    for (int c = 0; c < 8; c++) {
//                        reshapeInData[board][cnt] = newmatrix[a][c][b];
//                        cnt++;
//                    }
//                }
//            }
//        }
//        float[][] predMatrixRaw = new float[5][384];
//        tflite.run(reshapeInData, predMatrixRaw);
//
//        float[][][][] reshapePredData = new float[5][6][8][8];
//        int cnt = 0;
//        for (int a = 0; a < 6; a++) { //piece
//            for (int b = 0; b < 8; b++) { //row
//                for (int c = 0; c < 8; c++) {//col
//                    reshapePredData[0][a][b][c] = predMatrixRaw[0][cnt];
//                    cnt++;
//                }
//            }
//        }
//
//
//        //for move pediction
//        float prob = 0;
//        int[][] board = new int[0][0];
//
//        Coordinates toPos;
//        toPos = new Coordinates(0, 0);
//        int fromRow = 0;
//        int fromCol = 0;
//        int toRow = 0;
//        int toCol = 0;
//        for (int x = 0; x < 6; x++) {
//            for (int row = 0; row < 8; row++) { // to Coords
//                for (int col = 0; col < 8; col++) {
//                    ArrayList<Coordinates> List = new ArrayList<>();
//                    Coordinates c;
//
//                    c = new Coordinates(row, col);
//
//                    Piece p = Board[row][col].getPiece();
//                    //fromSquare --> find Piece based on P that can legally move to toSquare
//                    //if(P == 0){
//                    //  for(all pawns){
//                    //getX & get Y
//
//                    //set case variable numbers for each piece x is a set variable for all
//                    if (!p.isWhite()) {
//                        boolean found = false;
//                        switch (x) { //switch the case numbers and display the designated piece
//                            case 0: //case for pawn
//
//                                for (int g = 0; g < 8; g++) { //from coords
//                                    for (int h = 0; h < 8; h++) {
//                                        if (Board[row][col].getPiece() != null & p instanceof Pawn) {
//                                            List = p.AllowedMoves(toPos, Board);
//                                            if (moveIsAllowed(List, c)) {
//                                                found = true;
//                                                if (reshapePredData[0][x][row][col] > prob) {
//                                                    //keepMove = legal move
//                                                    prob = reshapePredData[0][x][row][col];
//                                                    fromRow = row;
//                                                    fromCol = col;
//                                                    toRow = g;
//                                                    toCol = h;
//                                                }
//                                                continue;
//                                            }
//                                        }
//                                    }
//                                    if (found) {
//                                        continue;
//                                    }
//                                }
//                                break;
//                            case 1: //case for knight
//                                for (int g = 0; g < 8; g++) { //from coords
//                                    for (int h = 0; h < 8; h++) {
//                                        if (Board[row][col].getPiece() != null & p instanceof Knight) {
//                                            List = p.AllowedMoves(toPos, Board);
//                                            if (moveIsAllowed(List, c)) {
//                                                found = true;
//                                                if (reshapePredData[0][x][row][col] > prob) {
//                                                    //keepMove = legal move
//                                                    prob = reshapePredData[0][x][row][col];
//                                                    fromRow = row;
//                                                    fromCol = col;
//                                                    toRow = g;
//                                                    toCol = h;
//                                                }
//                                                continue;
//                                            }
//                                        }
//                                    }
//                                    if (found) {
//                                        continue;
//                                    }
//                                }
//                                break;
//
//                            case 2: //case for bishop
//                                for (int g = 0; g < 8; g++) { //from coords
//                                    for (int h = 0; h < 8; h++) {
//                                        if (Board[row][col].getPiece() != null & p instanceof Bishop) {
//                                            List = p.AllowedMoves(toPos, Board);
//                                            if (moveIsAllowed(List, c)) {
//                                                found = true;
//                                                if (reshapePredData[0][x][row][col] > prob) {
//                                                    //keepMove = legal move
//                                                    prob = reshapePredData[0][x][row][col];
//                                                    fromRow = row;
//                                                    fromCol = col;
//                                                    toRow = g;
//                                                    toCol = h;
//                                                }
//                                                continue;
//                                            }
//                                        }
//                                    }
//                                    if (found) {
//                                        continue;
//                                    }
//                                }
//                                break;
//                            case 3: //case for rook
//                                for (int g = 0; g < 8; g++) { //from coords
//                                    for (int h = 0; h < 8; h++) {
//                                        if (Board[row][col].getPiece() != null & p instanceof Rook) {
//                                            List = p.AllowedMoves(toPos, Board);
//                                            if (moveIsAllowed(List, c)) {
//                                                found = true;
//                                                if (reshapePredData[0][x][row][col] > prob) {
//                                                    //keepMove = legal move
//                                                    prob = reshapePredData[0][x][row][col];
//                                                    fromRow = row;
//                                                    fromCol = col;
//                                                    toRow = g;
//                                                    toCol = h;
//                                                }
//                                                continue;
//                                            }
//                                        }
//                                    }
//                                    if (found) {
//                                        continue;
//                                    }
//                                }
//                                break;
//                            case 4: // case for Queen
//                                for (int g = 0; g < 8; g++) { //from coords
//                                    for (int h = 0; h < 8; h++) {
//                                        if (Board[row][col].getPiece() != null & p instanceof Queen) {
//                                            List = p.AllowedMoves(toPos, Board);
//                                            if (moveIsAllowed(List, c)) {
//                                                found = true;
//                                                if (reshapePredData[0][x][row][col] > prob) {
//                                                    //keepMove = legal move
//                                                    prob = reshapePredData[0][x][row][col];
//                                                    fromRow = row;
//                                                    fromCol = col;
//                                                    toRow = g;
//                                                    toCol = h;
//                                                }
//                                                continue;
//                                            }
//                                        }
//                                    }
//                                    if (found) {
//                                        continue;
//                                    }
//                                }
//                                break;
//                            case 5: //case for King
//                                for (int g = 0; g < 8; g++) { //from coords
//                                    for (int h = 0; h < 8; h++) {
//                                        if (Board[row][col].getPiece() != null & p instanceof King) {
//                                            List = p.AllowedMoves(toPos, Board);
//                                            if (moveIsAllowed(List, c)) {
//                                                found = true;
//                                                if (reshapePredData[0][x][row][col] > prob) {
//                                                    //keepMove = legal move
//                                                    prob = reshapePredData[0][x][row][col];
//                                                    fromRow = row;
//                                                    fromCol = col;
//                                                    toRow = g;
//                                                    toCol = h;
//                                                }
//                                                continue;
//                                            }
//                                        }
//                                    }
//                                    if (found) {
//                                        continue;
//                                    }
//                                }
//                                break;
//                        }
//                        Board2[toRow][toCol].setPiece(Board[fromRow][fromCol].getPiece());
//                    }
//
//
//                }
//
//            }
//
    // }
    //}
}