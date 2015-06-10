///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package chromosomtesting;
//
///**
// *
// * @author jakob
// */
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//
//import org.apache.batik.transcoder.Transcoder;
//import org.apache.batik.transcoder.TranscoderException;
//import org.apache.batik.transcoder.TranscoderInput;
//import org.apache.batik.transcoder.TranscoderOutput;
//import org.apache.fop.svg.PDFTranscoder;
//
//public class Test {
//    public static void main(String[] argv) throws TranscoderException, FileNotFoundException {
//        Transcoder transcoder = new PDFTranscoder();
//        TranscoderInput transcoderInput = new TranscoderInput(new FileInputStream(new File("Nukleosoms.svg")));
//        TranscoderOutput transcoderOutput = new TranscoderOutput(new FileOutputStream(new File("test.pdf")));
//        transcoder.transcode(transcoderInput, transcoderOutput);
//    }
//}