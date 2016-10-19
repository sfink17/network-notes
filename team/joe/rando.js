/** This function will create randomly placed nodes with edges between them
  *@param n  Number of Nodes
  *@param e  Number of Edges
  *@param x  Max X Coordinate
  *@param y  Max Y Coordinate

  *@return graph  an array that contains an array of nodes & an array of edges  --> graph{ nodes[], edges[] }
**/
function randomGraph(n, e, x, y) {
    //This is an array that ultimately gets returned
    var graph = { nodes: [], edges: [] };
    //temp variables
    tmpEdges = []; 
    var i, j, k;

        for (i = 0; i < n; i++) 
        {
            //Randomly generate n Nodes with random x and y coordinates
            graph.nodes.push({ label: 'node '+i, x: Math.floor((Math.random() * x) + 1), y: Math.floor((Math.random() * y) + 1)});

            //create a a handful of edges. we will select a subset of theses later.
            for (j = i+1; j < n; j++) 
            {
                tmpEdges.push({ source: i, target: j, len: 0 });
            }
        }

        // pick m random edges from tmpEdges
        k = tmpEdges.length - 1;
            for (i = 0; i < e; i++) 
            {
                graph.edges.push(tmpEdges.splice(Math.floor(Math.random()*k), 1)[0]);
                k--;
            }

        //find the length of each edge
        var x1, x2, y1, y2, distance;
        for (i = 0; i < e; i++)
        {
            x1 = graph.nodes[graph.edges[i].source].x;
            y1 = graph.nodes[graph.edges[i].source].y;
            x2 = graph.nodes[graph.edges[i].target].x;
            y2 = graph.nodes[graph.edges[i].target].y;

            //distance formula
            distance = Math.sqrt( Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2) ) ;
            //adding distance to the edges
            graph.edges[i].len = distance;
        }    
    return graph;
}

function testGraph(graph)
{
    for( i = 0; i < g.edges.length; i++)
    {
        console.log(graph.edges[i].source + " : " + graph.edges[i].target); 
    }
}

g = randomGraph(10, 15, 600, 600);
testGraph(g);
