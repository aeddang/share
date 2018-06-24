package lib.player;


import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.widget.FrameLayout;
import java.io.FileNotFoundException;
import java.io.IOException;

import lib.CustomTimer;
import lib.ViewUtil;



public class Player extends FrameLayout implements MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener ,
		SurfaceHolder.Callback,MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnCompletionListener,CustomTimer.TimerDelegate
{
	public static final int PLAYER_ERROR_CONNECT = 400;

	protected CustomTimer timer;
	protected SurfaceView screen;

	protected MediaPlayer video;
	protected boolean isPlaying,isAutoPlay,isInitPlay;
	protected Context context;
	protected int duration = -1;
	public Player(Context _context) {

		super(_context);
		context=_context;
	}
	public Player(Context _context, AttributeSet attrs) {

		super(_context,attrs,0);
		context=_context;
	}
	public Player(Context _context, AttributeSet attrs, int defStyle)
	{
		super(_context, attrs, defStyle);
		context=_context;
	}

	public void initPlayer(boolean needGetStatus)
	{


		isInitPlay=false;
		doInit();
		if(screen == null)
		{
			screen=new SurfaceView(context);
			LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			this.addView(screen,0,layout);
		}
		addPlayer();
		setReset();
		if(needGetStatus == true)
		{
			createStatusChecker();
		}


	}
	public void toglePlay()
	{
		if(video ==null)
		{
			return;
		}
		if(video.isPlaying()==true && isInitPlay==true){
			video.pause();
		}else
		{
			video.start();
		}
	}

	public void play()
	{
		if(video ==null)
		{
			return;
		}

		video.start();

	}
	public void pause()
	{
		if(video ==null)
		{
			return;
		}
		video.pause();

	}

	public void remove()
	{
		removeStatusChecker();
		removePlayer();
		doRemove();
	}
	public int getCurrentTime(){
		return video.getCurrentPosition();

	}
	private void createStatusChecker()
	{
		removeStatusChecker();
		timer=new CustomTimer(100,-1,this);
		timer.timerStart();
	}
	private void removeStatusChecker()
	{
		if(timer!=null){

			timer.removeTimer();
			timer=null;
		}
	}


	private void addPlayer(){

		video=new MediaPlayer();
		SurfaceHolder videoHolder = screen.getHolder();
		videoHolder.addCallback(this);
		isAutoPlay=false;
		video.setOnErrorListener(this);
		video.setOnPreparedListener(this);
		video.setOnBufferingUpdateListener(this);
		video.setOnCompletionListener(this);

	}
	private void setReset() {
		isPlaying=false;
		isInitPlay=false;
		duration = -1;
	}
	private void loadInit(boolean autoPlay)
	{
		setReset();
		isAutoPlay=autoPlay;
		if(isAutoPlay==true){
			onStallStarted();
		}
		onPause();

	}

	public void load(String path, boolean autoPlay) {
		loadInit(autoPlay);

		try {
			video.setDataSource(path);
			video.prepareAsync();

		} catch (IllegalArgumentException e) {
			doError(PLAYER_ERROR_CONNECT,400);
		} catch (SecurityException e) {
			doError(PLAYER_ERROR_CONNECT,403);
		} catch (IllegalStateException e) {
			doError(PLAYER_ERROR_CONNECT,400);
		} catch (IOException e) {

			doError(PLAYER_ERROR_CONNECT,404);
		}
	}

	public void loadAsset(Activity ac, String assetID , boolean autoPlay) {
		loadInit(autoPlay);
		try {
			AssetFileDescriptor afd;
			try
			{
				afd = ac.getAssets().openFd(assetID);
			}
			catch (FileNotFoundException e)
			{
				doError(PLAYER_ERROR_CONNECT,404);
				return;
			}
			video.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(), afd.getLength());
			video.prepareAsync();

		} catch (IllegalArgumentException e) {
			doError(PLAYER_ERROR_CONNECT,400);
		} catch (SecurityException e) {
			doError(PLAYER_ERROR_CONNECT,403);
		} catch (IllegalStateException e) {
			doError(PLAYER_ERROR_CONNECT,400);
		} catch (IOException e) {
			doError(PLAYER_ERROR_CONNECT,404);
		}

	}



	@Override
	public void onPrepared(MediaPlayer mp) {

		if(isAutoPlay==true){
			onStallEnded();
			video.start();
			isAutoPlay=false;

		}

	}
	@Override
	public void onTime(CustomTimer timer){
		if(video.isPlaying()==true && isPlaying==false){

			onPlay();
		}else if(video.isPlaying()==false && isPlaying==true){

			onPause();
		}
		onTimeUpdate();

	}
	public void onComplete(CustomTimer timer){}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

		//video.getVideoWidth();
		//video.getVideoHeight();


	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i("", "surfaceCreated");
		if(isInitPlay==false) {
			video.setDisplay(holder);
			isInitPlay=true;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	private void removePlayer() {

		if(video!=null) {
			if(isInitPlay==true) {
				video.pause();
			}
			video.release();
			video = null;
		}
		ViewUtil.remove(this);
	}

	public boolean onError(MediaPlayer mp, int what, int extra){

		doError(what,extra);
		return true;
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent){
		Log.i("","BUFFER : "+percent);
		doBufferingUpdate(percent);

	}

	@Override
	public void onCompletion(MediaPlayer mp){
		doCompletion();
	}

	private void onPlay() {
		isPlaying=true;
		if(isInitPlay==false){
			isInitPlay=true;
		}
		doPlay();

	}
	private void onPause() {
		isPlaying=false;
		doPause();
	}

	private void onTimeUpdate() {
		int d=video.getDuration();
		if(duration == -1 && d > 0)
		{
			duration = d;
			doDurationUpdate();
		}
		doTimeUpdate(video.getCurrentPosition());

	}
	private void onStallStarted()
	{
		doStallStarted();
	}
	private void onStallEnded()
	{
		doStallEnded();
	}
	protected void doInit()
	{

	}
	protected void doRemove()
	{

	}
	protected void doBufferingUpdate(int percent)
	{

	}
	protected void doCompletion()
	{

	}
	protected void doStallStarted()
	{

	}
	protected void doStallEnded()
	{

	}
	protected void doPlay()
	{

	}
	protected void doPause()
	{

	}
	protected void doDurationUpdate()
	{

	}
	protected void doTimeUpdate(int t)
	{

	}

	protected void doError(int what, int extra)
	{

	}


}
