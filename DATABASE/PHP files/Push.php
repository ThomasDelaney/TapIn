<?php
	class Push 
	{
		private $title;
		private $message;
	 
		function __construct($title, $message)
		{
			 $this->title = $title;
			 $this->message = $message; 
		}
		
		public function getPush() 
		{
			$res = array();
			$res['data']['title'] = $this->title;
			$res['data']['message'] = $this->message;
			return $res;
		}
	}
?>