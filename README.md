# Anylogic Transformation to CSP

<p>&nbsp;</p>
<ul>
<li><strong>Component: </strong>Each component initialize by its first process.</li>
<li><strong>Variables:&nbsp;</strong>provides values to an action when it is triggered.</li>
<li><strong>Variable Properties:</strong></li>
<ul>
<li><strong>Name:</strong>&nbsp;The name of variable. It uses to identify the variable.</li>
<li><strong>Value: </strong>The initial value of variable.</li>
</ul>
<li><strong>State:&nbsp;</strong>represents a location of control with a particular set of reactions to conditions and/or events.</li>
<li><strong>State Properties:</strong></li>
<ul>
<li><strong>Id:&nbsp;</strong>&nbsp;The id of State. It is a unique 13 digit integer value.</li>
<li><strong>Name:</strong>&nbsp;The name of State. It uses to identify the State.</li>
<li><strong>Initialize:&nbsp;</strong>It defines either the State is initial or not. It has a Boolean type values i.e. true or false. In case of true it considers as an initial state.</li>
</ul>
</ul>
<ul>
<li><strong>Transition:</strong>&nbsp;A transition denotes a switch from one state to another. A transition indicates that if the specified trigger event occurs and the specified guard condition is true, the statechart switches from one state to another and performs the specified action.</li>
<li><strong>Transition Properties:</strong></li>
<ul>
<li><strong>Name:</strong>&nbsp;The name of Transition. It uses to identify the Transition.</li>
<li><strong>Guard:&nbsp;</strong>A guard is implemented on transitions. Transitions fire if the condition defined in guard comes true.</li>
<li><strong>Event:&nbsp;&nbsp;</strong>The name of Event. It has String data type.</li>
<li><strong>Properties:</strong></li>
<ul>
<li><strong>Source:&nbsp;</strong>The Name of the State to which it belongs.</li>
<li><strong>Target:&nbsp;</strong>The Name of the targeted State to which it has to move.</li>
<li><strong>Type:&nbsp;</strong>An event has two types:</li>
<ul>
<li><strong>Receive:&nbsp;</strong>It waits for a defined message expected from connected components to receive.</li>
<li><strong>Send</strong>:&nbsp; It sends message to the connected components with the time delay of zero seconds. A message can be of type &lsquo;String&rsquo; or a complex object containing message parameters.</li>
</ul>
<li><strong>Action:&nbsp;</strong>&nbsp;It implements the defined function. It uses to set input variables and write on to the output variables.</li>
<li><strong>TimeFunction:&nbsp;</strong></li>
<ul>
<li><strong>Type:&nbsp;</strong>It is the type of time constraint.</li>
<li><strong>Value:&nbsp;</strong>It stores the integer type time value.</li>
<li><strong>Unit:</strong> It is in seconds.</li>
</ul>
</ul>
</ul>
</ul>
