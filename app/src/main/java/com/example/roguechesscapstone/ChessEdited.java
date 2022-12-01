package com.example.roguechesscapstone;

import android.content.res.AssetFileDescriptor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roguechesscapstone.ml.RNNmodel50KGamesTanhCenter25B200E;
import com.example.roguechesscapstone.pieces.B;
import com.example.roguechesscapstone.pieces.Kin;
import com.example.roguechesscapstone.pieces.Kni;
import com.example.roguechesscapstone.pieces.Pa;
import com.example.roguechesscapstone.pieces.PieceMov;
import com.example.roguechesscapstone.pieces.Q;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;

import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;


public class ChessEdited extends AppCompatActivity implements View.OnClickListener {

    public com.example.roguechesscapstone.Position[][] CurrentBoard = new com.example.roguechesscapstone.Position[8][8]; //8 rows and 8 columns
    public com.example.roguechesscapstone.Position[][] TempBoard = new com.example.roguechesscapstone.Position[8][8];
    public Boolean FirstPlayer;
    public ArrayList<com.example.roguechesscapstone.Coord> coordList = new ArrayList<>();
    public com.example.roguechesscapstone.Coord PositionClicked = new com.example.roguechesscapstone.Coord(0, 0);
    public TextView game_over;
    public TextView[][] BoardDisplayed = new TextView[8][8]; //show board
    public TextView[][] BoardBackground = new TextView[8][8]; //tiles
    public Boolean SomethingSelected = false;
    public com.example.roguechesscapstone.Coord lastPos = null ; //start at null for last position
    public ArrayList<com.example.roguechesscapstone.Position[][]> LastMoves = new ArrayList<>();
    public LinearLayout pawn_choices; //
    public int NumOfMoves;

    //call the pieces
    PieceMov BlackPawn;
    PieceMov BlackPawn2;
    PieceMov BlackPawn3;
    PieceMov BlackPawn4;
    PieceMov BlackPawn5;
    PieceMov BlackPawn6;
    PieceMov BlackPawn7;
    PieceMov BlackPawn8;

    PieceMov WhitePawn;
    PieceMov WhitePawn2;
    PieceMov WhitePawn3;
    PieceMov WhitePawn4;
    PieceMov WhitePawn5;
    PieceMov WhitePawn6;
    PieceMov WhitePawn7;
    PieceMov WhitePawn8;

    PieceMov BlackKing;
    PieceMov WhiteKing;

    PieceMov BlackQueen;
    PieceMov WhiteQueen;

    PieceMov BlackBishop;
    PieceMov BlackBishop2;
    PieceMov WhiteBishop;
    PieceMov WhiteBishop2;

    PieceMov BlackKnight;
    PieceMov BlackKnight2;
    PieceMov WhiteKnight;
    PieceMov WhiteKnight2;

    PieceMov BlackRook;
    PieceMov BlackRook2;
    PieceMov WhiteRook;
    PieceMov WhiteRook2;


    Interpreter tflite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN); //get a stable fullscreen layout
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(com.example.roguechesscapstone.R.layout.activity_game); //use layout in activity game xlm

        ChessBoardInitial(); //start board

        game_over = (TextView)findViewById(com.example.roguechesscapstone.R.id.game_over); //when game over use this
        pawn_choices = (LinearLayout)findViewById(com.example.roguechesscapstone.R.id.pawnchoices); //when you hit other side dispolay this

        //hide both for the mean time
        game_over.setVisibility(View.INVISIBLE);
        pawn_choices.setVisibility(View.INVISIBLE);

        try {
                    RNNmodel50KGamesTanhCenter25B200E model = RNNmodel50KGamesTanhCenter25B200E.newInstance(ChessEdited.this); //was getApplicationContext()

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 5, 384}, DataType.FLOAT32);
                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 5 * 384);

                    byteBuffer.order(ByteOrder.nativeOrder());

                    inputFeature0.loadBuffer(byteBuffer);

                    // Runs model inference and gets result.
                    RNNmodel50KGamesTanhCenter25B200E.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    // Releases model resources if no longer used.
                    model.close();
                } catch (IOException ex) {
                     System.out.print("Failed");//TODO Handle the exception
                }
    }


    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("RNNmodel50KGamesTanhCenter25B200E");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declareLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declareLength);
    }



    private void ChessBoardInitial() {

        //initialize all the pieces, the white pieces have the function "white" as true
        BlackKing = new Kin(false);
        WhiteKing = new Kin(true);

        BlackQueen = new Q(false);
        WhiteQueen = new Q(true);

        BlackRook = new com.example.roguechesscapstone.pieces.R(false);
        BlackRook2 = new com.example.roguechesscapstone.pieces.R(false);
        WhiteRook = new com.example.roguechesscapstone.pieces.R(true);
        WhiteRook2 = new com.example.roguechesscapstone.pieces.R(true);

        BlackKnight = new Kni(false);
        BlackKnight2 = new Kni(false);
        WhiteKnight = new Kni(true);
        WhiteKnight2 = new Kni(true);

        BlackBishop = new B(false);
        BlackBishop2 = new B(false);
        WhiteBishop = new B(true);
        WhiteBishop2 = new B(true);

        BlackPawn = new Pa(false);
        BlackPawn2 = new Pa(false);
        BlackPawn3 = new Pa(false);
        BlackPawn4 = new Pa(false);
        BlackPawn5 = new Pa(false);
        BlackPawn6 = new Pa(false);
        BlackPawn7 = new Pa(false);
        BlackPawn8 = new Pa(false);

        WhitePawn = new Pa(true);
        WhitePawn2 = new Pa(true);
        WhitePawn3 = new Pa(true);
        WhitePawn4 = new Pa(true);
        WhitePawn5 = new Pa(true);
        WhitePawn6 = new Pa(true);
        WhitePawn7 = new Pa(true);
        WhitePawn8 = new Pa(true);

        //the board positioning
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                CurrentBoard[i][j] = new Position(null);
                TempBoard[i][j] = new Position(null);
            }
        }
        //position all the pieces in their designated starting position
        //white main pieces
        CurrentBoard[0][7].setPiece(WhiteRook);
        CurrentBoard[1][7].setPiece(WhiteKnight);
        CurrentBoard[2][7].setPiece(WhiteBishop);
        CurrentBoard[3][7].setPiece(WhiteQueen);
        CurrentBoard[4][7].setPiece(WhiteKing);
        CurrentBoard[5][7].setPiece(WhiteBishop2);
        CurrentBoard[6][7].setPiece(WhiteKnight2);
        CurrentBoard[7][7].setPiece(WhiteRook2);

        //8 pawns for white
        CurrentBoard[0][6].setPiece(WhitePawn);
        CurrentBoard[1][6].setPiece(WhitePawn2);
        CurrentBoard[2][6].setPiece(WhitePawn3);
        CurrentBoard[3][6].setPiece(WhitePawn4);
        CurrentBoard[4][6].setPiece(WhitePawn5);
        CurrentBoard[5][6].setPiece(WhitePawn6);
        CurrentBoard[6][6].setPiece(WhitePawn7);
        CurrentBoard[7][6].setPiece(WhitePawn8);

        //black main pieces
        CurrentBoard[0][0].setPiece(BlackRook);
        CurrentBoard[1][0].setPiece(BlackKnight);
        CurrentBoard[2][0].setPiece(BlackBishop);
        CurrentBoard[3][0].setPiece(BlackQueen);
        CurrentBoard[4][0].setPiece(BlackKing);
        CurrentBoard[5][0].setPiece(BlackBishop2);
        CurrentBoard[6][0].setPiece(BlackKnight2);
        CurrentBoard[7][0].setPiece(BlackRook2);

        //8 black pawns
        CurrentBoard[0][1].setPiece(BlackPawn);
        CurrentBoard[1][1].setPiece(BlackPawn2);
        CurrentBoard[2][1].setPiece(BlackPawn3);
        CurrentBoard[3][1].setPiece(BlackPawn4);
        CurrentBoard[4][1].setPiece(BlackPawn5);
        CurrentBoard[5][1].setPiece(BlackPawn6);
        CurrentBoard[6][1].setPiece(BlackPawn7);
        CurrentBoard[7][1].setPiece(BlackPawn8);

        //Create the coordinate positioning of the board along with the ending background board
        //The First row (XY) Coordinates
        BoardDisplayed[0][0] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R00);
        BoardBackground[0][0] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R000);
        BoardDisplayed[1][0] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R10);
        BoardBackground[1][0] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R010);
        BoardDisplayed[2][0] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R20);
        BoardBackground[2][0] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R020);
        BoardDisplayed[3][0] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R30);
        BoardBackground[3][0] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R030);
        BoardDisplayed[4][0] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R40);
        BoardBackground[4][0] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R040);
        BoardDisplayed[5][0] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R50);
        BoardBackground[5][0] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R050);
        BoardDisplayed[6][0] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R60);
        BoardBackground[6][0] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R060);
        BoardDisplayed[7][0] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R70);
        BoardBackground[7][0] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R070);
        //Second Row
        BoardDisplayed[0][1] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R01);
        BoardBackground[0][1] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R001);
        BoardDisplayed[1][1] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R11);
        BoardBackground[1][1] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R011);
        BoardDisplayed[2][1] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R21);
        BoardBackground[2][1] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R021);
        BoardDisplayed[3][1] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R31);
        BoardBackground[3][1] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R031);
        BoardDisplayed[4][1] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R41);
        BoardBackground[4][1] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R041);
        BoardDisplayed[5][1] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R51);
        BoardBackground[5][1] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R051);
        BoardDisplayed[6][1] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R61);
        BoardBackground[6][1] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R061);
        BoardDisplayed[7][1] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R71);
        BoardBackground[7][1] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R071);
        //Third Row
        BoardDisplayed[0][2] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R02);
        BoardBackground[0][2] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R002);
        BoardDisplayed[1][2] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R12);
        BoardBackground[1][2] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R012);
        BoardDisplayed[2][2] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R22);
        BoardBackground[2][2] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R022);
        BoardDisplayed[3][2] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R32);
        BoardBackground[3][2] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R032);
        BoardDisplayed[4][2] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R42);
        BoardBackground[4][2] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R042);
        BoardDisplayed[5][2] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R52);
        BoardBackground[5][2] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R052);
        BoardDisplayed[6][2] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R62);
        BoardBackground[6][2] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R062);
        BoardDisplayed[7][2] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R72);
        BoardBackground[7][2] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R072);
        //Fourth Row
        BoardDisplayed[0][3] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R03);
        BoardBackground[0][3] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R003);
        BoardDisplayed[1][3] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R13);
        BoardBackground[1][3] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R013);
        BoardDisplayed[2][3] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R23);
        BoardBackground[2][3] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R023);
        BoardDisplayed[3][3] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R33);
        BoardBackground[3][3] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R033);
        BoardDisplayed[4][3] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R43);
        BoardBackground[4][3] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R043);
        BoardDisplayed[5][3] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R53);
        BoardBackground[5][3] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R053);
        BoardDisplayed[6][3] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R63);
        BoardBackground[6][3] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R063);
        BoardDisplayed[7][3] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R73);
        BoardBackground[7][3] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R073);
        //Fifth Row
        BoardDisplayed[0][4] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R04);
        BoardBackground[0][4] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R004);
        BoardDisplayed[1][4] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R14);
        BoardBackground[1][4] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R014);
        BoardDisplayed[2][4] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R24);
        BoardBackground[2][4] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R024);
        BoardDisplayed[3][4] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R34);
        BoardBackground[3][4] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R034);
        BoardDisplayed[4][4] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R44);
        BoardBackground[4][4] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R044);
        BoardDisplayed[5][4] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R54);
        BoardBackground[5][4] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R054);
        BoardDisplayed[6][4] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R64);
        BoardBackground[6][4] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R064);
        BoardDisplayed[7][4] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R74);
        BoardBackground[7][4] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R074);
        //Sixth Row
        BoardDisplayed[0][5] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R05);
        BoardBackground[0][5] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R005);
        BoardDisplayed[1][5] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R15);
        BoardBackground[1][5] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R015);
        BoardDisplayed[2][5] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R25);
        BoardBackground[2][5] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R025);
        BoardDisplayed[3][5] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R35);
        BoardBackground[3][5] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R035);
        BoardDisplayed[4][5] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R45);
        BoardBackground[4][5] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R045);
        BoardDisplayed[5][5] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R55);
        BoardBackground[5][5] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R055);
        BoardDisplayed[6][5] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R65);
        BoardBackground[6][5] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R065);
        BoardDisplayed[7][5] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R75);
        BoardBackground[7][5] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R075);
        //Seventh row
        BoardDisplayed[0][6] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R06);
        BoardBackground[0][6] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R006);
        BoardDisplayed[1][6] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R16);
        BoardBackground[1][6] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R016);
        BoardDisplayed[2][6] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R26);
        BoardBackground[2][6] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R026);
        BoardDisplayed[3][6] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R36);
        BoardBackground[3][6] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R036);
        BoardDisplayed[4][6] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R46);
        BoardBackground[4][6] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R046);
        BoardDisplayed[5][6] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R56);
        BoardBackground[5][6] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R056);
        BoardDisplayed[6][6] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R66);
        BoardBackground[6][6] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R066);
        BoardDisplayed[7][6] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R76);
        BoardBackground[7][6] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R076);

        //Last Low for a total of 64 squares
        BoardDisplayed[0][7] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R07);
        BoardBackground[0][7] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R007);
        BoardDisplayed[1][7] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R17);
        BoardBackground[1][7] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R017);
        BoardDisplayed[2][7] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R27);
        BoardBackground[2][7] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R027);
        BoardDisplayed[3][7] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R37);
        BoardBackground[3][7] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R037);
        BoardDisplayed[4][7] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R47);
        BoardBackground[4][7] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R047);
        BoardDisplayed[5][7] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R57);
        BoardBackground[5][7] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R057);
        BoardDisplayed[6][7] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R67);
        BoardBackground[6][7] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R067);
        BoardDisplayed[7][7] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R77);
        BoardBackground[7][7] = (TextView) findViewById(com.example.roguechesscapstone.R.id.R077);
        //for loop to get the pieces and set the pieces
        for(int y=0;y<8;y++){
            for(int z=0;z<8;z++){
                if(CurrentBoard[y][z].getPiece()==null){
                    TempBoard[y][z].setPiece(null);
                }else{
                    TempBoard[y][z].setPiece(CurrentBoard[y][z].getPiece());
                }
            }
        }
        //set the instances to 0 but first player (white) makes first move
        NumOfMoves = 0;
        SomethingSelected = false;
        FirstPlayer = true;
        BoardPlacement();
    }


    private void BoardPlacement() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                PieceMov pieceMov = CurrentBoard[i][j].getPiece(); //get piece piece

                int x;

                //set case variable numbers for each piece x is a set variable for all
                if (CurrentBoard[i][j].getPiece() != null) {
                    if (pieceMov instanceof Kin) x = 0;
                    else if (pieceMov instanceof Q)
                        x = 1;
                    else if (pieceMov instanceof com.example.roguechesscapstone.pieces.R)
                        x = 2;
                    else if (pieceMov instanceof B)
                        x = 3;
                    else if (pieceMov instanceof Kni)
                        x = 4;
                    else if (pieceMov instanceof Pa)
                        x = 5;
                    else x = 6;
                    switch (x) { //switch the case numbers and display the designated piece
                        case 0: //case for kings
                            if (pieceMov.isWhite()) {
                                BoardDisplayed[i][j].setBackgroundResource(R.drawable.wking);
                            } else {
                                BoardDisplayed[i][j].setBackgroundResource(R.drawable.bking);
                            }
                            break;

                        case 1: //case for queens
                            if (pieceMov.isWhite()) {
                                BoardDisplayed[i][j].setBackgroundResource(com.example.roguechesscapstone.R.drawable.wqueen);
                            } else {
                                BoardDisplayed[i][j].setBackgroundResource(com.example.roguechesscapstone.R.drawable.bqueen);
                            }
                            break;

                        case 2: //case for rooks
                            if (pieceMov.isWhite()) {
                                BoardDisplayed[i][j].setBackgroundResource(com.example.roguechesscapstone.R.drawable.wrook);
                            } else {
                                BoardDisplayed[i][j].setBackgroundResource(com.example.roguechesscapstone.R.drawable.brook);
                            }
                            break;

                        case 3: //case for bishops
                            if (pieceMov.isWhite()) {
                                BoardDisplayed[i][j].setBackgroundResource(com.example.roguechesscapstone.R.drawable.wbishop);
                            } else {
                                BoardDisplayed[i][j].setBackgroundResource(com.example.roguechesscapstone.R.drawable.bbishop);
                            }
                            break;

                        case 4: // case for knights
                            if (pieceMov.isWhite()) {
                                BoardDisplayed[i][j].setBackgroundResource(com.example.roguechesscapstone.R.drawable.wknight);
                            } else {
                                BoardDisplayed[i][j].setBackgroundResource(com.example.roguechesscapstone.R.drawable.bknight);
                            }
                            break;

                        case 5: //case for pawns
                            if (pieceMov.isWhite()) {
                                BoardDisplayed[i][j].setBackgroundResource(com.example.roguechesscapstone.R.drawable.wpawn);
                            } else {
                                BoardDisplayed[i][j].setBackgroundResource(com.example.roguechesscapstone.R.drawable.bpawn);
                            }
                            break;

                        default:

                    }
                }else{ //else just display board
                    BoardDisplayed[i][j].setBackgroundResource(0);
                }
            }
        }
        KingDanger(); //if king is in danger call function
    }

    //New Matrix


    @Override
    public void onClick(View v) {
        //same thing as above but case variables for positions when clicked
        switch (v.getId()) {
            case com.example.roguechesscapstone.R.id.R00: //beginning coordinates
                PositionClicked = new com.example.roguechesscapstone.Coord(0, 0);
                break;
            case com.example.roguechesscapstone.R.id.R10: //when clicked update 1 row 2 column
                PositionClicked.setX(1);
                PositionClicked.setY(0);
                break;
            case com.example.roguechesscapstone.R.id.R20: //when clicked update 1 row 3 column
                PositionClicked.setX(2);
                PositionClicked.setY(0);
                break;
            case com.example.roguechesscapstone.R.id.R30: //when clicked update 1 row 4 column
                PositionClicked.setX(3);
                PositionClicked.setY(0);
                break;
            case com.example.roguechesscapstone.R.id.R40: //when clicked update 1 row 5 column
                PositionClicked.setX(4);
                PositionClicked.setY(0);
                break;
            case com.example.roguechesscapstone.R.id.R50:  //when clicked update 1 row 6 column
                PositionClicked.setX(5);
                PositionClicked.setY(0);
                break;
            case com.example.roguechesscapstone.R.id.R60: //when clicked update 1 row 7 column
                PositionClicked.setX(6);
                PositionClicked.setY(0);
                break;
            case com.example.roguechesscapstone.R.id.R70: //when clicked update 1 row 8 column
                PositionClicked.setX(7);
                PositionClicked.setY(0);
                break;

            case com.example.roguechesscapstone.R.id.R01: //when clicked update 2 row 1 column
                PositionClicked.setX(0);
                PositionClicked.setY(1);
                break;
            case com.example.roguechesscapstone.R.id.R11: //when clicked update 2 row 2 column
                PositionClicked.setX(1);
                PositionClicked.setY(1);
                break;
            case com.example.roguechesscapstone.R.id.R21: //when clicked update 2 row 3 column
                PositionClicked.setX(2);
                PositionClicked.setY(1);
                break;
            case com.example.roguechesscapstone.R.id.R31: //when clicked update 2 row 4 column
                PositionClicked.setX(3);
                PositionClicked.setY(1);
                break;
            case com.example.roguechesscapstone.R.id.R41: //when clicked update 2 row 5 column
                PositionClicked.setX(4);
                PositionClicked.setY(1);
                break;
            case com.example.roguechesscapstone.R.id.R51://when clicked update 2 row 6 column
                PositionClicked.setX(5);
                PositionClicked.setY(1);
                break;
            case com.example.roguechesscapstone.R.id.R61: //when clicked update 2 row 7 column
                PositionClicked.setX(6);
                PositionClicked.setY(1);
                break;
            case com.example.roguechesscapstone.R.id.R71: //when clicked update 2 row 8 column
                PositionClicked.setX(7);
                PositionClicked.setY(1);
                break;

            case com.example.roguechesscapstone.R.id.R02: //when clicked update 3 row 1 column
                PositionClicked.setX(0);
                PositionClicked.setY(2);
                break;
            case com.example.roguechesscapstone.R.id.R12: //when clicked update 3 row 2 column
                PositionClicked.setX(1);
                PositionClicked.setY(2);
                break;
            case com.example.roguechesscapstone.R.id.R22: //when clicked update 3 row 3 column
                PositionClicked.setX(2);
                PositionClicked.setY(2);
                break;
            case com.example.roguechesscapstone.R.id.R32: //when clicked update 3 row 4 column
                PositionClicked.setX(3);
                PositionClicked.setY(2);
                break;
            case com.example.roguechesscapstone.R.id.R42: //when clicked update 3 row 5 column
                PositionClicked.setX(4);
                PositionClicked.setY(2);
                break;
            case com.example.roguechesscapstone.R.id.R52: //when clicked update 3 row 6 column
                PositionClicked.setX(5);
                PositionClicked.setY(2);
                break;
            case com.example.roguechesscapstone.R.id.R62: //when clicked update 3 row 7 column
                PositionClicked.setX(6);
                PositionClicked.setY(2);
                break;
            case com.example.roguechesscapstone.R.id.R72: //when clicked update 3 row 8 column
                PositionClicked.setX(7);
                PositionClicked.setY(2);
                break;
            //continue with the pattern
            case com.example.roguechesscapstone.R.id.R03:
                PositionClicked.setX(0);
                PositionClicked.setY(3);
                break;
            case com.example.roguechesscapstone.R.id.R13:
                PositionClicked.setX(1);
                PositionClicked.setY(3);
                break;
            case com.example.roguechesscapstone.R.id.R23:
                PositionClicked.setX(2);
                PositionClicked.setY(3);
                break;
            case com.example.roguechesscapstone.R.id.R33:
                PositionClicked.setX(3);
                PositionClicked.setY(3);
                break;
            case com.example.roguechesscapstone.R.id.R43:
                PositionClicked.setX(4);
                PositionClicked.setY(3);
                break;
            case com.example.roguechesscapstone.R.id.R53:
                PositionClicked.setX(5);
                PositionClicked.setY(3);
                break;
            case com.example.roguechesscapstone.R.id.R63:
                PositionClicked.setX(6);
                PositionClicked.setY(3);
                break;
            case com.example.roguechesscapstone.R.id.R73:
                PositionClicked.setX(7);
                PositionClicked.setY(3);
                break;

            case com.example.roguechesscapstone.R.id.R04:
                PositionClicked.setX(0);
                PositionClicked.setY(4);
                break;
            case com.example.roguechesscapstone.R.id.R14:
                PositionClicked.setX(1);
                PositionClicked.setY(4);
                break;
            case com.example.roguechesscapstone.R.id.R24:
                PositionClicked.setX(2);
                PositionClicked.setY(4);
                break;
            case com.example.roguechesscapstone.R.id.R34:
                PositionClicked.setX(3);
                PositionClicked.setY(4);
                break;
            case com.example.roguechesscapstone.R.id.R44:
                PositionClicked.setX(4);
                PositionClicked.setY(4);
                break;
            case com.example.roguechesscapstone.R.id.R54:
                PositionClicked.setX(5);
                PositionClicked.setY(4);
                break;
            case com.example.roguechesscapstone.R.id.R64:
                PositionClicked.setX(6);
                PositionClicked.setY(4);
                break;
            case com.example.roguechesscapstone.R.id.R74:
                PositionClicked.setX(7);
                PositionClicked.setY(4);
                break;

            case com.example.roguechesscapstone.R.id.R05:
                PositionClicked.setX(0);
                PositionClicked.setY(5);
                break;
            case com.example.roguechesscapstone.R.id.R15:
                PositionClicked.setX(1);
                PositionClicked.setY(5);
                break;
            case com.example.roguechesscapstone.R.id.R25:
                PositionClicked.setX(2);
                PositionClicked.setY(5);
                break;
            case com.example.roguechesscapstone.R.id.R35:
                PositionClicked.setX(3);
                PositionClicked.setY(5);
                break;
            case com.example.roguechesscapstone.R.id.R45:
                PositionClicked.setX(4);
                PositionClicked.setY(5);
                break;
            case com.example.roguechesscapstone.R.id.R55:
                PositionClicked.setX(5);
                PositionClicked.setY(5);
                break;
            case com.example.roguechesscapstone.R.id.R65:
                PositionClicked.setX(6);
                PositionClicked.setY(5);
                break;
            case com.example.roguechesscapstone.R.id.R75:
                PositionClicked.setX(7);
                PositionClicked.setY(5);
                break;

            case com.example.roguechesscapstone.R.id.R06:
                PositionClicked.setX(0);
                PositionClicked.setY(6);
                break;
            case com.example.roguechesscapstone.R.id.R16:
                PositionClicked.setX(1);
                PositionClicked.setY(6);
                break;
            case com.example.roguechesscapstone.R.id.R26:
                PositionClicked.setX(2);
                PositionClicked.setY(6);
                break;
            case com.example.roguechesscapstone.R.id.R36:
                PositionClicked.setX(3);
                PositionClicked.setY(6);
                break;
            case com.example.roguechesscapstone.R.id.R46:
                PositionClicked.setX(4);
                PositionClicked.setY(6);
                break;
            case com.example.roguechesscapstone.R.id.R56:
                PositionClicked.setX(5);
                PositionClicked.setY(6);
                break;
            case com.example.roguechesscapstone.R.id.R66:
                PositionClicked.setX(6);
                PositionClicked.setY(6);
                break;
            case com.example.roguechesscapstone.R.id.R76:
                PositionClicked.setX(7);
                PositionClicked.setY(6);
                break;

            case com.example.roguechesscapstone.R.id.R07:
                PositionClicked.setX(0);
                PositionClicked.setY(7);
                break;
            case com.example.roguechesscapstone.R.id.R17:
                PositionClicked.setX(1);
                PositionClicked.setY(7);
                break;
            case com.example.roguechesscapstone.R.id.R27:
                PositionClicked.setX(2);
                PositionClicked.setY(7);
                break;
            case com.example.roguechesscapstone.R.id.R37:
                PositionClicked.setX(3);
                PositionClicked.setY(7);
                break;
            case com.example.roguechesscapstone.R.id.R47:
                PositionClicked.setX(4);
                PositionClicked.setY(7);
                break;
            case com.example.roguechesscapstone.R.id.R57:
                PositionClicked.setX(5);
                PositionClicked.setY(7);
                break;
            case com.example.roguechesscapstone.R.id.R67:
                PositionClicked.setX(6);
                PositionClicked.setY(7);
                break;
            case com.example.roguechesscapstone.R.id.R77:
                PositionClicked.setX(7);
                PositionClicked.setY(7);
                break;
        }
        //Starts analyzing pieces
        if (!SomethingSelected) { //if nothing is selected
            if (CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].getPiece() == null) { //if board state does not have a piece
                KingDanger(); //check if king is danger
                return;
            }

            if (FirstPlayer == false) { //if black's turn

                //Initialize matrix
                int[][][] newmatrix = new int[6][8][8];
                for (int a = 0; a < 6; a++) {
                    for (int b = 0; b < 8; b++) {
                        for (int c = 0; c < 8; c++) {
                            newmatrix[a][b][c] = 0;
                        }
                    }
                }
                for (int b = 0; b < 8; b++) {
                    for (int c = 0; c < 8; c++) {
                        PieceMov d = CurrentBoard[b][c].getPiece(); //get piece p
                        if (CurrentBoard[b][c].getPiece() != null) {
                            if (d instanceof Kin) {
                                if (d.isWhite()) {
                                    newmatrix[0][b][c] = -1;
                                } else {
                                    newmatrix[0][b][c] = 1;
                                }
                            } else if (d instanceof Q) {
                                if (d.isWhite()) {
                                    newmatrix[4][b][c] = -1;
                                } else {
                                    newmatrix[4][b][c] = 1;
                                }
                            } else if (d instanceof com.example.roguechesscapstone.pieces.R) {
                                if (d.isWhite()) {
                                    newmatrix[3][b][c] = -1;
                                } else {
                                    newmatrix[3][b][c] = 1;
                                }
                            } else if (d instanceof B) {
                                if (d.isWhite()) {
                                    newmatrix[2][b][c] = -1;
                                } else {
                                    newmatrix[2][b][c] = 1;
                                }
                            } else if (d instanceof Kni) {
                                if (d.isWhite()) {
                                    newmatrix[1][b][c] = -1;
                                } else {
                                    newmatrix[1][b][c] = 1;
                                }
                            } else if (d instanceof Pa) {
                                if (d.isWhite()) {
                                    newmatrix[0][b][c] = -1;
                                } else {
                                    newmatrix[0][b][c] = 1;
                                }
                            }

                        }

                    }
                }

                float[][] reshapeInData = new float[5][384];
                for (int board = 0; board < 5; board++) {
                    int cnt = 0;
                    for (int a = 0; a < 6; a++) {
                        for (int b = 0; b < 8; b++) {
                            for (int c = 0; c < 8; c++) {
                                reshapeInData[board][cnt] = newmatrix[a][c][b];
                                cnt++;
                            }
                        }
                    }
                }
                float[][] predMatrixRaw = new float[5][384];
                //try {
                    //Interpreter interpreter = new Interpreter(loadModelFile());
                    tflite.run(reshapeInData, predMatrixRaw);
//                }catch (IOException e){
//                    System.out.print("try again");
//                }

                float[][][][] reshapePredData = new float[5][6][8][8];
                int cnt = 0;
                for (int a = 0; a < 6; a++) { //piece
                    for (int b = 0; b < 8; b++) { //row
                        for (int c = 0; c < 8; c++) {//col
                            reshapePredData[0][a][b][c] = predMatrixRaw[0][cnt];
                            cnt++;
                        }
                    }
                }


                //for move pediction
                float prob = 0;
                int[][] board = new int[0][0];

                com.example.roguechesscapstone.Coord toPos;
                toPos = new com.example.roguechesscapstone.Coord(0, 0);
                com.example.roguechesscapstone.Coord c;
                c = new com.example.roguechesscapstone.Coord(0, 0);
                int fromRow = 0;
                int fromCol = 0;
                int toRow = 0;
                int toCol = 0;
                for (int x = 0; x < 6; x++) {
                    for (int row = 0; row < 8; row++) { // to Coords
                        for (int col = 0; col < 8; col++) {
                            ArrayList<com.example.roguechesscapstone.Coord> List = new ArrayList<>();


                            c = new com.example.roguechesscapstone.Coord(row, col);

                            PieceMov p = CurrentBoard[row][col].getPiece();
                            //fromSquare --> find Piece based on P that can legally move to toSquare
                            //if(P == 0){
                            //  for(all pawns){
                            //getX & get Y

                            //set case variable numbers for each piece x is a set variable for all
                            if (!p.isWhite()) {
                                boolean found = false;
                                switch (x) { //switch the case numbers and display the designated piece
                                    case 0: //case for pawn

                                        for (int g = 0; g < 8; g++) { //from coords
                                            for (int h = 0; h < 8; h++) {
                                                if (CurrentBoard[row][col].getPiece() != null & p instanceof Pa) {
                                                    List = p.ValidMoves(toPos, CurrentBoard);
                                                    if (MoveValid(List, c)) {
                                                        found = true;
                                                        if (reshapePredData[0][x][row][col] > prob) {
                                                            //keepMove = legal move
                                                            prob = reshapePredData[0][x][row][col];
                                                            fromRow = row;
                                                            fromCol = col;
                                                            toRow = g;
                                                            toCol = h;
                                                        }
                                                        continue;
                                                    }
                                                }
                                            }
                                            if (found) {
                                                continue;
                                            }
                                        }
                                        break;
                                    case 1: //case for knight
                                        for (int g = 0; g < 8; g++) { //from coords
                                            for (int h = 0; h < 8; h++) {
                                                if (CurrentBoard[row][col].getPiece() != null & p instanceof Kni) {
                                                    List = p.ValidMoves(toPos, CurrentBoard);
                                                    if (MoveValid(List, c)) {
                                                        found = true;
                                                        if (reshapePredData[0][x][row][col] > prob) {
                                                            //keepMove = legal move
                                                            prob = reshapePredData[0][x][row][col];
                                                            fromRow = row;
                                                            fromCol = col;
                                                            toRow = g;
                                                            toCol = h;
                                                        }
                                                        continue;
                                                    }
                                                }
                                            }
                                            if (found) {
                                                continue;
                                            }
                                        }
                                        break;

                                    case 2: //case for bishop
                                        for (int g = 0; g < 8; g++) { //from coords
                                            for (int h = 0; h < 8; h++) {
                                                if (CurrentBoard[row][col].getPiece() != null & p instanceof B) {
                                                    List = p.ValidMoves(toPos, CurrentBoard);
                                                    if (MoveValid(List, c)) {
                                                        found = true;
                                                        if (reshapePredData[0][x][row][col] > prob) {
                                                            //keepMove = legal move
                                                            prob = reshapePredData[0][x][row][col];
                                                            fromRow = row;
                                                            fromCol = col;
                                                            toRow = g;
                                                            toCol = h;
                                                        }
                                                        continue;
                                                    }
                                                }
                                            }
                                            if (found) {
                                                continue;
                                            }
                                        }
                                        break;
                                    case 3: //case for rook
                                        for (int g = 0; g < 8; g++) { //from coords
                                            for (int h = 0; h < 8; h++) {
                                                if (CurrentBoard[row][col].getPiece() != null & p instanceof com.example.roguechesscapstone.pieces.R) {
                                                    List = p.ValidMoves(toPos, CurrentBoard);
                                                    if (MoveValid(List, c)) {
                                                        found = true;
                                                        if (reshapePredData[0][x][row][col] > prob) {
                                                            //keepMove = legal move
                                                            prob = reshapePredData[0][x][row][col];
                                                            fromRow = row;
                                                            fromCol = col;
                                                            toRow = g;
                                                            toCol = h;
                                                        }
                                                        continue;
                                                    }
                                                }
                                            }
                                            if (found) {
                                                continue;
                                            }
                                        }
                                        break;
                                    case 4: // case for Queen
                                        for (int g = 0; g < 8; g++) { //from coords
                                            for (int h = 0; h < 8; h++) {
                                                if (CurrentBoard[row][col].getPiece() != null & p instanceof Q) {
                                                    List = p.ValidMoves(toPos, CurrentBoard);
                                                    if (MoveValid(List, c)) {
                                                        found = true;
                                                        if (reshapePredData[0][x][row][col] > prob) {
                                                            //keepMove = legal move
                                                            prob = reshapePredData[0][x][row][col];
                                                            fromRow = row;
                                                            fromCol = col;
                                                            toRow = g;
                                                            toCol = h;
                                                        }
                                                        continue;
                                                    }
                                                }
                                            }
                                            if (found) {
                                                continue;
                                            }
                                        }
                                        break;
                                    case 5: //case for King
                                        for (int g = 0; g < 8; g++) { //from coords
                                            for (int h = 0; h < 8; h++) {
                                                if (CurrentBoard[row][col].getPiece() != null & p instanceof Kin) {
                                                    List = p.ValidMoves(toPos, CurrentBoard);
                                                    if (MoveValid(List, c)) {
                                                        found = true;
                                                        if (reshapePredData[0][x][row][col] > prob) {
                                                            //keepMove = legal move
                                                            prob = reshapePredData[0][x][row][col];
                                                            fromRow = row;
                                                            fromCol = col;
                                                            toRow = g;
                                                            toCol = h;
                                                        }
                                                        continue;
                                                    }
                                                }
                                            }
                                            if (found) {
                                                continue;
                                            }
                                        }
                                        break;
                                }
                                //Board[toRow][toCol].setPiece(Board[fromRow][fromCol].getPiece());

                            }


                        }
                    }
                    saveCurrentBoard();
                    if (CurrentBoard[toRow][toCol].getPiece() instanceof Kin) { //Checking if King was taken over
                        if (CurrentBoard[toRow][toCol].getPiece().isWhite() != FirstPlayer) {
                            game_over.setVisibility(View.VISIBLE);
                        }
                    }
                    CurrentBoard[toRow][toCol].setPiece(CurrentBoard[fromRow][fromCol].getPiece());
                    CurrentBoard[fromRow][fromCol].setPiece(null);

                    //reset everything
                    resetBoxColorOfPosition(coordList);
                    BoardDisplayed[fromRow][fromCol].setBackgroundResource(0);
                    resetBoxOfLastPosition(c);
                    SomethingSelected = false;
                    FirstPlayer = true;//Goes to False
                    CheckIfPawn();


                }


            } // end black turn
            else {
                //First player turn = true at this point
                if (CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].getPiece().isWhite() != FirstPlayer) { //else piece is white but False
                    KingDanger(); //check if king is in danger
                    return;
                } else { //Game over and reset
                    coordList.clear(); //clear coordinates
                    coordList = CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].getPiece().ValidMoves(PositionClicked, CurrentBoard); //list of available positions
                    BoardBackground[PositionClicked.getX()][PositionClicked.getY()].setBackgroundResource(com.example.roguechesscapstone.R.color.teal_200); //highlight position selected
                    BoxColorOfPosition(coordList);
                    SomethingSelected = true; //move to else
                }
            }
        } else { //If something is selected (default move)
            if (CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].getPiece() == null) { //Selected square doesn't have a piece
                if (MoveValid(coordList, PositionClicked)) { //if move is allowed get the coordinates
                    saveCurrentBoard(); //save position
                    if (CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].getPiece() instanceof Kin) { //piece is king
                        if (CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].getPiece().isWhite() != FirstPlayer) { //False
                            game_over.setVisibility(View.VISIBLE); //if king is overtaken the game is over
                        }
                    }
                    CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].setPiece(CurrentBoard[lastPos.getX()][lastPos.getY()].getPiece());
                    CurrentBoard[lastPos.getX()][lastPos.getY()].setPiece(null); //last position on board

                    //king is in danger and reset

                    KingDanger();
                    resetBoxColorOfPosition(coordList);
                    matrix(); //called
                    BoardDisplayed[lastPos.getX()][lastPos.getY()].setBackgroundResource(0);
                    resetBoxOfLastPosition(lastPos);
                    SomethingSelected = false; //reset to nothing being selected
                    FirstPlayer = !FirstPlayer; //FirstPlayerTurn turns to False
                    CheckIfPawn(); //check the piece

                } else { //else last position is being used
                    resetBoxOfLastPosition(lastPos);
                    resetBoxColorOfPosition(coordList);
                    SomethingSelected = false;
                }
            } else { //if there is a piece
                if (CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].getPiece() == null) {
                    KingDanger();
                    return;

                }
            }

//else{
                if (CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].getPiece() != null) { //piece is present
                    if (CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].getPiece().isWhite() == FirstPlayer) { //False (Black Piece)
                        if (MoveValid(coordList, PositionClicked)) {
                            saveCurrentBoard();
                            if (CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].getPiece() instanceof Kin) { //Checking if King was taken over
                                if (CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].getPiece().isWhite() != FirstPlayer) {
                                    game_over.setVisibility(View.VISIBLE);
                                }
                            }
                            CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].setPiece(CurrentBoard[lastPos.getX()][lastPos.getY()].getPiece());
                            CurrentBoard[lastPos.getX()][lastPos.getY()].setPiece(null);

                            //reset everything
                            resetBoxColorOfPosition(coordList);
                            BoardDisplayed[lastPos.getX()][lastPos.getY()].setBackgroundResource(0);
                            resetBoxOfLastPosition(lastPos);
                            SomethingSelected = false;
                            FirstPlayer = false;//Goes to True
                            CheckIfPawn();

                        }
                    }
                } else {

                    if (CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].getPiece().isWhite() != FirstPlayer) { //True (White Turn)
                        KingDanger();
                        return;
                    }

                    resetBoxOfLastPosition(lastPos);
                    resetBoxColorOfPosition(coordList);
                    coordList.clear();
                    coordList = CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].getPiece().ValidMoves(PositionClicked, CurrentBoard);
                    BoardBackground[PositionClicked.getX()][PositionClicked.getY()].setBackgroundResource(com.example.roguechesscapstone.R.color.teal_200); //color for selection (Highlights)
                    BoxColorOfPosition(coordList);
                    SomethingSelected = true;
                }

                KingDanger();
                lastPos = new com.example.roguechesscapstone.Coord(PositionClicked.getX(), PositionClicked.getY()); //last position is saved
                BoardPlacement();
            }
        }




    public void saveCurrentBoard(){
        NumOfMoves++; //number of moves is set at 1 add for every loop
        LastMoves.add(NumOfMoves -1 , TempBoard); //add the moves

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                LastMoves.get(NumOfMoves -1)[i][j] = new com.example.roguechesscapstone.Position(null);
            }
        }

        for(int g=0;g<8;g++){
            for(int h=0;h<8;h++){
                if(CurrentBoard[g][h].getPiece()==null){
                    LastMoves.get(NumOfMoves -1)[g][h].setPiece(null); //if get piece is null (nothing clicked) set piece is null
                }else{
                    LastMoves.get(NumOfMoves -1)[g][h].setPiece(CurrentBoard[g][h].getPiece()); //else save last position of last move
                }
            }
        }
    }
    //undo a move if needed
    public void undo(View v){
        if(NumOfMoves >0) {

            for(int g=0;g<8;g++){
                for(int h=0;h<8;h++){
                    if(LastMoves.get(NumOfMoves -1)[g][h].getPiece()==null){ //get the last move and subtract by one
                        CurrentBoard[g][h].setPiece(null);
                    }else{
                        CurrentBoard[g][h].setPiece(LastMoves.get(NumOfMoves -1)[g][h].getPiece()); //else set the piece at last move
                    }
                }
            }
            NumOfMoves--; //subtract a number of moves

            BoardPlacement(); //set board
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if((i+j)%2==0){
                        BoardBackground[i][j].setBackgroundResource(com.example.roguechesscapstone.R.color.colorBoardDark); //color of tiles
                    }else{
                        BoardBackground[i][j].setBackgroundResource(com.example.roguechesscapstone.R.color.colorBoardLight); //color of tiles
                    }
                }
            }
            KingDanger();
            matrix();//called
            FirstPlayer = !FirstPlayer;
            game_over.setVisibility(View.INVISIBLE);
        }
    }

    //get the pawn of choice either black or white
    public void PawnChoice(View v) { //for pawn promotion when a pawn gets to the end
        int x = v.getId(); //get the id from the drawables
        switch (x) {
            case com.example.roguechesscapstone.R.id.pawn_queen: //if piece is queen
                if (PositionClicked.getY() == 0) {
                    CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].setPiece(new Q(true)); //if piece is true then it is white
                    BoardDisplayed[PositionClicked.getX()][PositionClicked.getY()].setBackgroundResource(com.example.roguechesscapstone.R.drawable.wqueen);//draw the queen of position clicked
                } else {
                    CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].setPiece(new Q(false));//if piece is black
                    BoardDisplayed[PositionClicked.getX()][PositionClicked.getY()].setBackgroundResource(com.example.roguechesscapstone.R.drawable.bqueen);//draw the black queen in position clicked from drawables
                }
                break;
            case com.example.roguechesscapstone.R.id.pawn_bishop: //if piece is Bishop
                if (PositionClicked.getY() == 0) {
                    CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].setPiece(new B(true));
                    BoardDisplayed[PositionClicked.getX()][PositionClicked.getY()].setBackgroundResource(com.example.roguechesscapstone.R.drawable.wbishop);
                } else {
                    CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].setPiece(new B(false));
                    BoardDisplayed[PositionClicked.getX()][PositionClicked.getY()].setBackgroundResource(com.example.roguechesscapstone.R.drawable.bbishop);
                }
                break;
            case com.example.roguechesscapstone.R.id.pawn_rook://if piece is rook
                if (PositionClicked.getY() == 0) {
                    CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].setPiece(new com.example.roguechesscapstone.pieces.R(true));
                    BoardDisplayed[PositionClicked.getX()][PositionClicked.getY()].setBackgroundResource(com.example.roguechesscapstone.R.drawable.wrook);//draw the white rook in position clicked from drawables
                } else {
                    CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].setPiece(new com.example.roguechesscapstone.pieces.R(false));
                    BoardDisplayed[PositionClicked.getX()][PositionClicked.getY()].setBackgroundResource(com.example.roguechesscapstone.R.drawable.brook);//draw the black rook in position clicked from drawables
                }
                break;
            case com.example.roguechesscapstone.R.id.pawn_knight://if piece is a knight
                if (PositionClicked.getY() == 0) {
                    CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].setPiece(new Kni(true));
                    BoardDisplayed[PositionClicked.getX()][PositionClicked.getY()].setBackgroundResource(com.example.roguechesscapstone.R.drawable.wknight);
                } else {
                    CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].setPiece(new Kni(false));
                    BoardDisplayed[PositionClicked.getX()][PositionClicked.getY()].setBackgroundResource(com.example.roguechesscapstone.R.drawable.bknight);

                }
                break;
        }
        pawn_choices.setVisibility(View.INVISIBLE);//set them invisible for the mean time until it is time to display
    }
    // reset color at allowed position
    private void resetBoxColorOfPosition(ArrayList<com.example.roguechesscapstone.Coord> ListOfCoord) {
        for(int i=0; i<ListOfCoord.size(); i++){
            if((ListOfCoord.get(i).getX() + ListOfCoord.get(i).getY())%2==0){
                BoardBackground[ListOfCoord.get(i).getX()][ListOfCoord.get(i).getY()].setBackgroundResource(com.example.roguechesscapstone.R.color.colorBoardDark);
            }else {
                BoardBackground[ListOfCoord.get(i).getX()][ListOfCoord.get(i).getY()].setBackgroundResource(com.example.roguechesscapstone.R.color.colorBoardLight);
            }
        }
    }

    //reset the color of allowed positions
    void BoxColorOfPosition(ArrayList<com.example.roguechesscapstone.Coord> list){

        for(int i=0; i<list.size(); i++){
            if(CurrentBoard[list.get(i).getX()][list.get(i).getY()].getPiece() == null){
                BoardBackground[list.get(i).getX()][list.get(i).getY()].setBackgroundResource(com.example.roguechesscapstone.R.color.colorPositionAvailable);
            }else{
                BoardBackground[list.get(i).getX()][list.get(i).getY()].setBackgroundResource(com.example.roguechesscapstone.R.color.colorDanger);
            }
        }
    }

    //check if the move is allowed
    private boolean MoveValid(ArrayList<com.example.roguechesscapstone.Coord> piece, com.example.roguechesscapstone.Coord Coord) {
        Boolean Valid = false;
        for(int i =0;i<piece.size();i++){
            if(piece.get(i).getX() == Coord.getX()  &&  piece.get(i).getY() == Coord.getY()){
                Valid = true;
                break;
            }
        }
        return Valid;
    }

    //reset the color at the last position
    private void resetBoxOfLastPosition(com.example.roguechesscapstone.Coord lastPos){
        if((lastPos.getX() + lastPos.getY())%2==0){
            BoardBackground[lastPos.getX()][lastPos.getY()].setBackgroundResource(com.example.roguechesscapstone.R.color.colorBoardDark);
        }else {
            BoardBackground[lastPos.getX()][lastPos.getY()].setBackgroundResource(com.example.roguechesscapstone.R.color.colorBoardLight);
        }
    }

    //check if the king is in danger
    private void KingDanger() {
        ArrayList<Coord> List = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (CurrentBoard[i][j].getPiece() != null) {
                    List.clear();
                    Coord cor = new Coord(i, j);
                    List = CurrentBoard[i][j].getPiece().ValidMoves(cor, CurrentBoard); //List is the allowed moves

                    for (int x = 0; x < List.size(); x++) {
                        if (CurrentBoard[List.get(x).getX()][List.get(x).getY()].getPiece() instanceof Kin) { //get piece and check if its the king

                            if ((List.get(x).getX() + List.get(x).getY()) % 2 == 0) { //if the king is not in danger then it is the normal background
                                BoardBackground[List.get(x).getX()][List.get(x).getY()].setBackgroundResource(com.example.roguechesscapstone.R.color.colorBoardDark); //brownish background for every 2 tiles (evens)
                            } else {
                                BoardBackground[List.get(x).getX()][List.get(x).getY()].setBackgroundResource(com.example.roguechesscapstone.R.color.colorBoardLight); //else yellowish background for every other tile (odds)
                            }

                            if (CurrentBoard[i][j].getPiece().isWhite() != CurrentBoard[List.get(x).getX()][List.get(x).getY()].getPiece().isWhite()) { //If the king is danger
                                BoardBackground[List.get(x).getX()][List.get(x).getY()].setBackgroundResource(com.example.roguechesscapstone.R.color.colorKingInDanger);// display the color red
                            }
                        }
                    }
                }
            }
        }
    }

    private void CheckIfPawn(){ //look for which pawn piece is chosen
        if(CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].getPiece() instanceof Pa){
            if(CurrentBoard[PositionClicked.getX()][PositionClicked.getY()].getPiece().isWhite()){
                if(PositionClicked.getY() == 0){
                    pawn_choices.setVisibility(View.VISIBLE);
                }
            }else{
                if(PositionClicked.getY() == 7){
                    pawn_choices.setVisibility(View.VISIBLE);
                    pawn_choices.setRotation(180);
                }
            }
        }
        KingDanger(); //check if king is in danger
    }
    //New Matrix
    private void matrix(){
        int[][][] matrix = new int [6][8][8];
        for(int a =0; a <6; a++) {
            for (int b = 0; b < 8; b++) {
                for (int c = 0; c < 8; c++) {
                    matrix[a][b][c] = 0;
                }
            }
        }
        for (int b = 0; b < 8; b++) {
            for (int c = 0; c < 8; c++) {
                PieceMov d = CurrentBoard[b][c].getPiece(); //get piece p
                    if (CurrentBoard[b][c].getPiece() != null) {
                       if (d instanceof Kin) {
                           if (d.isWhite()) {
                              matrix[0][b][c] = -6;
                           } else {
                              matrix[0][b][c] = 6;
                           }
                       } else if (d instanceof Q) {
                           if (d.isWhite()) {
                               matrix[4][b][c] = -5;
                           } else {
                               matrix[4][b][c] = 5;
                           }
                       } else if (d instanceof com.example.roguechesscapstone.pieces.R) {
                           if (d.isWhite()) {
                               matrix[3][b][c] = -4;
                           } else {
                               matrix[3][b][c] = 4;
                           }
                       } else if (d instanceof B) {
                           if (d.isWhite()) {
                               matrix[2][b][c] = -3;
                           } else {
                               matrix[2][b][c] = 3;
                           }
                       } else if (d instanceof Kni) {
                           if (d.isWhite()) {
                               matrix[1][b][c] = -2;
                           } else {
                               matrix[1][b][c] = 2;
                           }
                       } else if (d instanceof Pa) {
                           if (d.isWhite()) {
                              matrix[0][b][c] = -1;
                           } else {
                              matrix[0][b][c] = 1;
                           }
                       }

                }

            }
        }
        System.out.println(Arrays.deepToString(matrix));
    }
}




